package si.smartspent.smartspent;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import si.smartspent.smartspent.ProfileView.PointsTab;
import si.smartspent.smartspent.ProfileView.PointsView;
import si.smartspent.smartspent.ProfileView.TabAdapter;
import si.smartspent.smartspent.ProfileView.TransactionsTab;

public class ProfileActivity extends DrawerActivity {
    private TextView email;
    private TextView username;
    private TextView firstname;
    private TextView lastname;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private PointsView pView;
    private TextView points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        email = (TextView) findViewById(R.id.email);
        username = (TextView) findViewById(R.id.username);
        firstname = (TextView) findViewById(R.id.firstname);
        lastname = (TextView) findViewById(R.id.lastname);
        mViewPager = (ViewPager) findViewById(R.id.mPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PointsTab(), "Points");
        adapter.addFragment(new TransactionsTab(), "Transactions");
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        pView = (PointsView) findViewById(R.id.pointsView);
        points = (TextView) findViewById(R.id.textView_points);
        //populateUI();
    }

    private void populateUI() {
        email.setText(Utils.getEmail(this));
        username.setText(Utils.getUsername(this));
        firstname.setText(Utils.getFirstname(this));
        lastname.setText(Utils.getLastname(this));
        //points.setText(Utils.getPoints(this));
    }
}
