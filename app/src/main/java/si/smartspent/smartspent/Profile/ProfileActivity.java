package si.smartspent.smartspent.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import si.smartspent.smartspent.CustomViews.CircleImageView;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import si.smartspent.smartspent.DrawerActivity;
import si.smartspent.smartspent.GeneticAlgorithm.Chromosome;
import si.smartspent.smartspent.R;
import si.smartspent.smartspent.Utils;

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

    private TextView monthSpent;
    private TextView estSpending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        email = (TextView) findViewById(R.id.email);
        username = (TextView) findViewById(R.id.username);
        name = (TextView) findViewById(R.id.name);
        birth_date = (TextView) findViewById(R.id.birth_date);
        country = (TextView) findViewById(R.id.country);
        avatar = (CircleImageView) findViewById(R.id.avatar);

        monthSpent = (TextView) findViewById(R.id.tV_spent_this_month);
        estSpending = (TextView) findViewById(R.id.tV_estimated_spending_next_month);

        // Get user data from Shared Preferences
        username.setText(Utils.getUserData(this, "username"));
        name.setText(Utils.getFullName(this));
        email.setText(Utils.getUserData(this, "email"));
        birth_date.setText(Utils.getUserData(this, "birth_date"));
        country.setText(Utils.getUserData(this, "country"));

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

    private void estimateSpending() {

    }
}
