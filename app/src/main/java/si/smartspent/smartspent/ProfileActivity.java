package si.smartspent.smartspent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends DrawerActivity {
    private TextView email;
    private TextView username;
    private TextView firstname;
    private TextView lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        email = (TextView) findViewById(R.id.email);
        username = (TextView) findViewById(R.id.username);
        firstname = (TextView) findViewById(R.id.firstname);
        lastname = (TextView) findViewById(R.id.lastname);

        populateUI();
    }

    private void populateUI() {
        email.setText(Utils.getEmail(this));
        username.setText(Utils.getUsername(this));
        firstname.setText(Utils.getFirstname(this));
        lastname.setText(Utils.getLastname(this));
    }
}
