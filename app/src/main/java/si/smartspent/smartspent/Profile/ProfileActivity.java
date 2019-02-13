package si.smartspent.smartspent.Profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import si.smartspent.smartspent.CustomViews.CircleImageView;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import si.smartspent.smartspent.DrawerActivity;
import si.smartspent.smartspent.R;
import si.smartspent.smartspent.Utils;

import static si.smartspent.smartspent.Utils.API_URL;

public class ProfileActivity extends DrawerActivity {
    private static final String TAG = ProfileActivity.class.getName();
    private TextView email;
    private TextView username;
    private TextView name;
    private TextView birth_date;
    private TextView country;
    private CircleImageView avatar;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private PointsView pView;
    private TextView points;

    private org.json.simple.JSONArray jsonArray;
    private TextView monthSpent;
    private TextView estSpending;
    Float amountSpent;
    Integer amountCount;
    Float estimatedSpending;
    ArrayList<GeneticTransaction> population;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        new ProfileActivity.TransactionsDataTask().execute((Void) null);

        email = (TextView) findViewById(R.id.email);
        username = (TextView) findViewById(R.id.username);
        name = (TextView) findViewById(R.id.name);
        birth_date = (TextView) findViewById(R.id.birth_date);
        country = (TextView) findViewById(R.id.country);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        points = (TextView) findViewById(R.id.points_holder);
        amountSpent = 0.0f;
        amountCount = 0;
        estimatedSpending = 0.0f;

        monthSpent = (TextView) findViewById(R.id.tV_spent_this_month);
        estSpending = (TextView) findViewById(R.id.tV_estimated_spending_next_month);

        Calendar c = Calendar.getInstance();
        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                String date = jsonObject.getString("date");
                String amount = jsonObject.getString("amount");

                Date ActualDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(date);
                c.setTime(ActualDate);
                DateFormat dateFormatY = new SimpleDateFormat("yyyy"); // Format to get current Year
                DateFormat dateFormatM = new SimpleDateFormat("MM"); // Format to get current Month
                if (c.get(Calendar.YEAR) == Integer.parseInt(dateFormatY.format(new Date())) && c.get(Calendar.MONTH) == Integer.parseInt(dateFormatM.format(new Date()))) { // Check date is in current Year and Month
                    amountSpent += Float.parseFloat(amount); // Add to amount spent this month
                    amountCount++;
                    population.add(new GeneticTransaction(ActualDate,Float.parseFloat(amount)));
                }
            }
            //monthSpent.setText("Spent this Month: " + amountSpent);
        } catch(Exception e) {
            e.printStackTrace();
        }

        monthSpent.setText("Spent this Month: " + amountSpent);

        try {
            Random random = new Random();
            for(int i = 0; i < population.size(); i++) {
                Integer chance = random.nextInt(3);
                switch(chance) {
                    case 0: { // Do nothing
                        break;
                    }
                    case 1: { // Crossbreed
                        population.set(i, cross(population.get(i), population.get(random.nextInt(population.size()))));
                        break;
                    }
                    case 2: { // Mutate
                        population.set(i, mutate(population.get(i)));
                        break;
                    }
                }
            }

            for(int i = 0; i < population.size(); i++) { // Accumulates estimated spending from the "genetic algorithm"
                estimatedSpending += population.get(i).getAmount();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        estSpending.setText("Estimated Spending Next Month: " + estimatedSpending);

        // Get user data from Shared Preferences
        username.setText(Utils.getUserData(this, "username"));
        name.setText(Utils.getFullName(this));
        email.setText(Utils.getUserData(this, "email"));
        birth_date.setText(Utils.getUserData(this, "birth_date"));
        country.setText(Utils.getUserData(this, "country"));
        points.setText(Utils.getUserData(this, "points"));

        // Get avatar URL from Shared Preferences, and put it into custom ImageView using Glide library
        Glide.with(getApplicationContext())
                .load(Utils.getUserData(this, "avatar"))
                .into(avatar);
        mViewPager = (ViewPager) findViewById(R.id.mPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PointsTab(), "Points");
        adapter.addFragment(new TransactionsTab(), "Transactions");
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        pView = (PointsView) findViewById(R.id.pointsView);
        points = (TextView) findViewById(R.id.textView_points);
    }

    private class TransactionsDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            final OkHttpClient client;

            try {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                client = builder.build();

                Request request = new Request.Builder()
                        .header("Content-Type", "application/json")
                        .url(API_URL + "transactions")
                        .get()
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    // parse JSON array string with transactions to a JSONArray
                    jsonArray = (org.json.simple.JSONArray) (new JSONParser()).parse(jsonString);
                }
            } catch (IOException e) {
                Log.e(TAG, "Could not execute HTTP request.");
            } catch (org.json.simple.parser.ParseException e) {
                Log.e(TAG, "Error while parsing JSON string.");
            }
            return null;
        }
    }

    public class GeneticTransaction { // Class for "Genetic Algorithm"
        private Date date;
         private Float amount;

        public GeneticTransaction() {
            date = new Date();
            amount = 0.0f;
        }

        public GeneticTransaction(Date d, Float a) {
            date = d;
            amount = a;
        }

        public void setDate(Date d) {
            date = d;
        }

        public void setAmount(Float a) {
            amount = a;
        }

        public void set(Date d, Float a) {
            date = d;
            amount = a;
        }

        public Date getDate() {
            return date;
        }

        public Float getAmount() {
            return amount;
        }
    }

    private GeneticTransaction mutate(GeneticTransaction oldTransaction) { // Mutates transaction by generating either a new date/amount or both
        GeneticTransaction newTransaction = new GeneticTransaction();
        Random random = new Random();
        Integer ranInt = random.nextInt(4);
        switch(ranInt) {
            case 0: { // Neither
                newTransaction.set(oldTransaction.getDate(), oldTransaction.getAmount());
                break;
            }
            case 1: { // Just Date
                Calendar cal = Calendar.getInstance();
                Integer d = random.nextInt(28);
                Integer h = random.nextInt(24);
                Integer m = random.nextInt(60);
                cal.set(oldTransaction.getDate().getYear(), oldTransaction.getDate().getMonth(), d, h, m);
                Date dat = cal.getTime();
                newTransaction.setDate(dat);
                newTransaction.setAmount(oldTransaction.getAmount());
                break;
            }
            case 2: { // Just Amount
                Float f = random.nextFloat() * (400.0f - 1.0f) + 1.0f; // generate random float from 1 to 400
                newTransaction.setAmount(f);
                newTransaction.setDate(oldTransaction.getDate());
                break;
            }
            case 3: { // Both
                Float f = random.nextFloat() * (400.0f - 1.0f) + 1.0f; // generate random float from 1 to 400
                newTransaction.setAmount(f);
                Calendar cal = Calendar.getInstance();
                Integer d = random.nextInt(28);
                Integer h = random.nextInt(24);
                Integer m = random.nextInt(60);
                cal.set(oldTransaction.getDate().getYear(), oldTransaction.getDate().getMonth(), d, h, m);
                Date dat = cal.getTime();
                newTransaction.setDate(dat);
                break;
            }
        }
        return newTransaction;
    }

    private GeneticTransaction cross(GeneticTransaction oldTransaction1, GeneticTransaction oldTransaction2) { // Crossbreeds two transactions by taking date from one and amount from another.
        Random random = new Random();
        GeneticTransaction newTransaction = new GeneticTransaction();

        // 50/50 chance for crossbreeding
        if(random.nextBoolean()) { // 1 - > Date | 2 - > Amount
            newTransaction.setDate(oldTransaction1.getDate());
            newTransaction.setAmount(oldTransaction2.getAmount());
        } else { // 1 - > Amount | 2 - > Date
            newTransaction.setDate(oldTransaction2.getDate());
            newTransaction.setAmount(oldTransaction1.getAmount());
        }

        return newTransaction;
    }
}
