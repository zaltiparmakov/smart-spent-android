package si.smartspent.smartspent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

import si.smartspent.smartspent.Transactions.Transactions;
import si.smartspent.smartspent.Transactions.TransactionsActivity;

public class DrawerActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    protected FrameLayout frameLayout;
    private TextView username;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.drawable.ic_drawer:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                                finish();
                                break;
                            case R.id.nav_logout:
                                Utils.logout(getApplicationContext());
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.nav_timer:
                                intent = new Intent(getApplicationContext(), TimerActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.nav_map:
                                intent = new Intent(getApplicationContext(), MapActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                        return true;
                    }
                }
        );

        View headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.txt_username);
        username.setText(Utils.getUsername(this));

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
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
    }
}
