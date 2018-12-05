package si.smartspent.smartspent;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import si.smartspent.smartspent.CustomViews.CircleImageView;
import si.smartspent.smartspent.Transactions.TransactionsActivity;

public class DrawerActivity extends AppCompatActivity {
    protected DrawerLayout mDrawerLayout;
    protected FrameLayout frameLayout;
    private TextView name;
    private CircleImageView avatar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_settings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // frame layout, used by other activities to inflate view into it
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_transactions:
                                intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_logout:
                                Utils.logout(getApplicationContext());
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                // call destroy method to destroy the activity
                                finish();
                                break;
                            case R.id.nav_map:
                                intent = new Intent(getApplicationContext(), MapActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.stats:
                                intent = new Intent(getApplicationContext(), StatsActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                }
        );

        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.name);
        name.setText(Utils.getFullName(this));

        avatar = (CircleImageView) headerView.findViewById(R.id.avatar);
        Glide.with(this)
                .load(Utils.getUserData(this, "avatar"))
                .into(avatar);

        // when user clicks on drawer header or email, go to profile activity
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }
        });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // rotate icon
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        int color = 0;
        try {
            color = getResources().getColor(R.color.colorAccent);
        } catch (Resources.NotFoundException e) {
            System.err.println("Color not found");
        }
        actionbar.setBackgroundDrawable(new ColorDrawable(color));
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionbar.setElevation(10);
    }
}
