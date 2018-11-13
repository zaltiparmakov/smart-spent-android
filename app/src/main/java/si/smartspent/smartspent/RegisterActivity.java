package si.smartspent.smartspent;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Thread.sleep;
import static si.smartspent.smartspent.Utils.API_URL;

/**
 * Registering a user to the app backend
 */
public class RegisterActivity extends AppCompatActivity {
    public final String TAG = getClass().getName();

    /**
     * Keep track of the register task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    private EditText mFullnameText;
    private EditText mEmailText;
    private EditText mPasswordText;
    private TextView mLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullnameText = (EditText) findViewById(R.id.full_name);
        mEmailText = (EditText) findViewById(R.id.email);
        mPasswordText = (EditText) findViewById(R.id.password);

        mLoginText = (TextView) findViewById(R.id.footer_already_signed);
        mLoginText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.signup_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mFullnameText.setError(null);
        mEmailText.setError(null);
        mPasswordText.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String fullname = mFullnameText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !Utils.isPasswordValid(password)) {
            mEmailText.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailText.setError(getString(R.string.error_field_required));
            focusView = mEmailText;
            cancel = true;
        } else if (!Utils.isEmailValid(email)) {
            mEmailText.setError(getString(R.string.error_invalid_email));
            focusView = mEmailText;
            cancel = true;
        }

        // Check if user entered name
        if(TextUtils.isEmpty(fullname)) {
            mFullnameText.setError(getString(R.string.error_field_required));
            focusView = mFullnameText;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // perform the user register attempt.
            mAuthTask = new UserRegisterTask(email, fullname, password);
            mAuthTask.execute((Void) null);
        }
    }


    /**
     * Represents an asynchronous task to register the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mFullname;
        private final String mPassword;

        UserRegisterTask(String email, String fullname, String password) {
            mEmail = email;
            mFullname = fullname;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            final OkHttpClient client;
            try {
                // Divide full name into tokens, and assign first and last name to variables
                // to send them to the backend
                ArrayList<String> name_tokens = new ArrayList<>();
                StringTokenizer tokenizer = new StringTokenizer(mFullname);
                while(tokenizer.hasMoreElements()) {
                    name_tokens.add(tokenizer.nextToken());
                }
                String firstname = name_tokens.get(0);
                String lastname = null;
                try {
                    lastname = name_tokens.get(1);
                } catch (IndexOutOfBoundsException e) {
                    // if there is no last name, add 1, on the backend will be added to the username
                    lastname = "1";
                }

                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                client = builder.build();
                RequestBody body = new FormBody.Builder()
                        .add("first_name", firstname)
                        .add("last_name", lastname)
                        .add("email", mEmailText.getText().toString())
                        .add("password", mPasswordText.getText().toString())
                        .build();
                Request req = new Request.Builder().header("Content-Type", "application/x-www-form-urlencoded")
                        .url(API_URL + "register")
                        .post(body).build();
                Response res = client.newCall(req).execute();

                String jsonData = res.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);

                if(!res.isSuccessful()) {
                    throw new IOException("Unexpected code " + res);
                } else {
                    return true;
                }
            } catch (ProtocolException e) {
                Log.i(TAG, e.getMessage());
            } catch (IOException e) {
                Log.i(TAG, e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if(success) {
                Toast.makeText(getApplicationContext(), R.string.register_success, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), R.string.register_unsuccess, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
