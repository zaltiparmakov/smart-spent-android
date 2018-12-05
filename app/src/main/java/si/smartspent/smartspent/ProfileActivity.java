package si.smartspent.smartspent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import si.smartspent.smartspent.CustomViews.CircleImageView;

import static si.smartspent.smartspent.Utils.API_URL;

public class ProfileActivity extends DrawerActivity {
    private static final String TAG = ProfileActivity.class.getName();
    private TextView email;
    private TextView username;
    private TextView name;
    private TextView birth_date;
    private TextView country;
    private TextView phone_number;
    private CircleImageView avatar;
    private Canvas points_holder_canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        email = (TextView) findViewById(R.id.email);
        username = (TextView) findViewById(R.id.username);
        name = (TextView) findViewById(R.id.name);
        birth_date = (TextView) findViewById(R.id.birth_date);
        country = (TextView) findViewById(R.id.country);
        phone_number = (TextView) findViewById(R.id.phone_number);
        avatar = (CircleImageView) findViewById(R.id.avatar);

        // Get user data from Shared Preferences
        username.setText(Utils.getUserData(this, "username"));
        name.setText(Utils.getFullName(this));
        email.setText(Utils.getUserData(this, "email"));
        birth_date.setText(Utils.getUserData(this, "birth_date"));
        country.setText(Utils.getUserData(this, "country"));
        phone_number.setText(Utils.getUserData(this, "phone_number"));

        // Get avatar from Shared Preferences, and put it into custom ImageView using Glide library
        Glide.with(getApplicationContext())
                .load(Utils.getUserData(this, "avatar"))
                .into(avatar);
    }
}
