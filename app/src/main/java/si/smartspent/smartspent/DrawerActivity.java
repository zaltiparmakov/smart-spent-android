package si.smartspent.smartspent;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
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
import si.smartspent.smartspent.Profile.ProfileActivity;
import si.smartspent.smartspent.Transactions.TransactionsActivity;

public class DrawerActivity extends AppCompatActivity {
    protected DrawerLayout mDrawerLayout;
    protected FrameLayout frameLayout;
    private TextView name;
    private CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // frame layout, used by other activities to inflate view into it
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // select Transactions menu as a default selected menu on start
        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_transactions:
                                intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_budgets:
                                intent = new Intent(getApplicationContext(), BudgetsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_stats:
                                intent = new Intent(getApplicationContext(), StatsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_map:
                                intent = new Intent(getApplicationContext(), MapActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_cardscan:
                                intent = new Intent(getApplicationContext(), NFCCardScanActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_news:
                                intent = new Intent(getApplicationContext(), NewsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_stock_market:
                                intent = new Intent(getApplicationContext(), StockMarketActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_settings:
                                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_about:
                                intent = new Intent(getApplicationContext(), AboutActivity.class);
                                startActivity(intent);
                                break;
                        }
                        // mark current menu item as selected
                        menuItem.setChecked(true);
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
        actionbar.setBackgroundDrawable(getDrawable(R.color.colorPrimary));
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionbar.setElevation(5);
    }

    public void logout(View view) {
        Utils.logout(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
