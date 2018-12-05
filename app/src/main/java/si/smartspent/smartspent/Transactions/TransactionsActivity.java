package si.smartspent.smartspent.Transactions;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ProtocolException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import si.smartspent.smartspent.DrawerActivity;
import si.smartspent.smartspent.Location;
import si.smartspent.smartspent.R;
import si.smartspent.smartspent.Utils;

import static si.smartspent.smartspent.Utils.API_URL;

public class TransactionsActivity extends DrawerActivity {
    private static final String TAG = TransactionsActivity.class.getName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Transaction> transactionsList;
    private RecyclerView.LayoutManager mLayoutManager;
    //TODO: ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // insert this activity into drawer layout
        getLayoutInflater().inflate(R.layout.activity_transactions, frameLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        transactionsList = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // when floating button is clicked, open new transaction activity in order to add new transaction
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.add_new_transaction);
        actionButton.setOnClickListener(e -> {
            startActivity(new Intent(this, NewTransactionActivity.class));
        });

        // TODO: REMOTE. ONLY FOR TEST PURPOSES
        new UserDataTask().execute((Void) null);
    }

    // TODO: REMOVE. ONLY FOR TEST PURPOSES. Filling shared preferences with user data
    public class UserDataTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            final OkHttpClient client;
            try {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                client = builder.build();

                Request req = new Request.Builder()
                        .header("Content-Type", "application/json")
                        .url(API_URL + "user")
                        .get()
                        .build();
                Response res = client.newCall(req).execute();

                String jsonData = res.body().string();
                JSONObject jsonObject = (JSONObject) (new JSONParser()).parse(jsonData);

                if(!res.isSuccessful()) {
                    throw new IOException("Unexpected code " + res);
                } else {
                    // put user data into shared preferences
                }
            } catch (ProtocolException e) {
                Log.e(TAG, e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class TransactionsDataTask extends AsyncTask<Void, Void, Boolean> {
        private JSONArray jsonArray;

        @Override
        protected Boolean doInBackground(Void... voids) {
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

                if(response.isSuccessful()) {
                    String jsonString = response.body().string();
                    // parse JSON array string with transactions to a JSONArray
                    jsonArray = (JSONArray) (new JSONParser()).parse(jsonString);
                    return true;
                }
            } catch (IOException e) {
                Log.e(TAG, "Could not execute HTTP request.");
            } catch (ParseException e) {
                Log.e(TAG, "Error while parsing JSON string.");
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                // create new transactions array from JSON array fetched via REST
                Transaction[] transactions = new Gson().fromJson(jsonArray.toString(), Transaction[].class);
                // convert array to list for using in recyclerview
                transactionsList = new ArrayList<>(Arrays.asList(transactions));
                fillData();
            }
        }
    }

    private void fillData() {
        // specify an adapter (see also next example)
        mAdapter = new TransactionsAdapter(transactionsList);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        // execute async task to get data from the endpoint
        new TransactionsDataTask().execute((Void) null);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Transaction transaction) {
        transactionsList.add(transaction);
        fillData();
    }
}
