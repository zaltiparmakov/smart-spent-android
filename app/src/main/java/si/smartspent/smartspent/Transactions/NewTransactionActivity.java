package si.smartspent.smartspent.Transactions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.shawnlin.numberpicker.NumberPicker;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import si.smartspent.smartspent.CustomViews.AutoCompleteEditView;
import si.smartspent.smartspent.DateTimePickerFragment;
import si.smartspent.smartspent.DateTimePickerFragment.DateTimeChangeListener;
import si.smartspent.smartspent.DrawerActivity;
import si.smartspent.smartspent.R;
import si.smartspent.smartspent.Utils;

import static si.smartspent.smartspent.Utils.API_URL;

public class NewTransactionActivity extends DrawerActivity implements DateTimeChangeListener {
    private static final String TAG = NewTransactionActivity.class.getName();
    private static final int REQUEST_MAP_LOCATION = 1;
    private static final int REQUEST_PLACE_PICKER = 2;
    private EditText transaction_description;
    private AutoCompleteEditView transaction_category;
    private TextView transaction_date;
    private Date date;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private TextView transaction_location;
    private LatLng latLng = null;
    private boolean locationPermissionGranted = false;
    private NumberPicker euroPicker;
    private NumberPicker centPicker;
    private double amount = 0;
    private Switch income_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_new_transaction, frameLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // change actionbar home symbol as back button, to get back to the parent activity
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        transaction_description = findViewById(R.id.transaction_description);
        transaction_category = findViewById(R.id.transaction_category);

        transaction_date = findViewById(R.id.transaction_date);
        // representation of date
        // set current date as date on the beginning
        date = new Date();
        transaction_date.setText(dateFormat.format(date));

        euroPicker = findViewById(R.id.euro_picker);
        centPicker = findViewById(R.id.cent_picker);

        income_switch = findViewById(R.id.income_switch);

        transaction_location = findViewById(R.id.transaction_location);
        // Get closest business places on Google Maps, depending on the current location
        PlaceDetectionClient mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);;
        @SuppressWarnings("MissingPermission") final
        Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
        placeResult.addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();

                // Set the count, handling cases where less than 5 entries are returned.
                int count;
                if (likelyPlaces.getCount() < 5) {
                    count = likelyPlaces.getCount();
                } else {
                    count = 5;
                }

                int i = 0;
                String mLikelyPlaceNames[] = new String[count];

                // Loop through the places, and add the closest one to the location field
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    // Build a list of likely places to show the user.
                    mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                    latLng = placeLikelihood.getPlace().getLatLng();
                    transaction_location.setText(mLikelyPlaceNames[i]);

                    if (++i > (count-1)) {
                        break;
                    }
                }

                // Release the place likelihood buffer, to avoid memory leaks.
                likelyPlaces.release();
            }
        });
    }

    /**
     * Display Fragment to select date and time
     * @param view
     */
    public void showDatePicker(View view) {
        DialogFragment fragment = new DateTimePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Listen for date changes. Called when date is changed in the DateTimePicker fragment
     * @param date
     */
    @Override
    public void onDateTimeChange(Date date) {
        // assign date and time to the variable, for use when saving transaction data
        this.date = date;

        // set date and time in the view
        transaction_date.setText(dateFormat.format(date));
    }

    public void changeLocation(View view) {
        /**
         * Display map with places around the user, to pick one place, and call for results
         */
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Return data from Google Map with selected place
         */
        if(requestCode == REQUEST_PLACE_PICKER && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(data, this);
            transaction_location.setText(place.getName());
            latLng = place.getLatLng();
        }
    }

    public void addTransaction(View view) {
        // convert euros and cents from the view into double value
        int eur = euroPicker.getValue();
        int cents = centPicker.getValue();
        String amountString = eur + "." + cents;
        amount = Double.parseDouble(amountString);

        String transactionDescription = transaction_description.getText().toString();
        String transactionCategory = transaction_category.getText().toString();

        if(transactionDescription.isEmpty()) {
            transaction_description.setError(getString(R.string.error_field_required));
            return;
        } else if(transactionCategory.isEmpty()) {
            transaction_category.setError(getString(R.string.error_field_required));
            return;
        }

        // create new transaction
        Transaction transaction = new Transaction(
                transactionDescription,
                Utils.latLngToString(latLng),
                amount,
                date.toString(),
                transactionCategory,
                income_switch.isActivated(),
                Transaction.PAYMENT_METHOD.CARD
        );

        new PostTransaction().execute(transaction);

        NavUtils.navigateUpFromSameTask(this);
    }

    class PostTransaction extends AsyncTask<Transaction, Void, Void> {
        @Override
        protected Void doInBackground(Transaction... transactions) {
            final OkHttpClient client;

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            client = builder.build();

            MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            Gson gson = new Gson();
            // create JSON string from Transaction object
            String jsonString = gson.toJson(transactions[0]);
            RequestBody body = RequestBody.create(TYPE_JSON, jsonString);
            Request request = new Request.Builder()
                    .url(API_URL + "transactions")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * Start new Activity using phone camera, and wait for results
     */
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public void scanBill(View view) {
        // if user does not have camera, return from the method
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Toast.makeText(this, "You don't have camera on your device", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // TODO: Apply OCR and get data into Item class and present to the user

        /**
         * Open integrated camera to take a picture, and save the image
         */
        Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takeImageIntent.resolveActivity(getPackageManager()) != null) {
            File photo = null;
            try {
                photo = createImageFile();
            } catch (IOException e) {
                Log.e(TAG, "Error while creating file");
            }

            if(photo != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "si.smartspent.smartspent",
                        photo
                );
                takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivity(takeImageIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("ddMMyyy_HHmm").format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        String currentPath = image.getAbsolutePath();
        return image;
    }

    public void viewScannedItems(View view) {
        Toast.makeText(this, getString(R.string.scan_bill_first), Toast.LENGTH_SHORT)
                .show();
    }
}
