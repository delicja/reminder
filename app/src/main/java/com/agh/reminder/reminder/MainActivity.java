package com.agh.reminder.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.agh.reminder.reminder.adapters.ReminderPagerAdapter;
import com.agh.reminder.reminder.custom.Interfaces.IActivitySelectorHandler;
import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.fragments.ActiveActivityListFragment;
import com.agh.reminder.reminder.fragments.ActivityListFragment;
import com.agh.reminder.reminder.models.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.SQLException;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, IActivitySelectorHandler {

    private DatabaseHelper databaseHelper;
    private ReminderPagerAdapter adapter;

    //public GoogleApiClient apiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);

        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Active").setTag("active"));
        tabLayout.addTab(tabLayout.newTab().setText("Activity list").setTag("all"));
        tabLayout.addTab(tabLayout.newTab().setText("Report").setTag("report"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ReminderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
/*
        apiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        apiClient.connect();
*/

    }

    public DatabaseHelper getDatabaseHelper() {
        return this.databaseHelper;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * 3000 - an interval for how often the API should check the user's activity
     *
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Intent intent = new Intent(this, ActivityRecognizedService.class);
        //PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(apiClient, 0, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityEnabled(Activity activity) {
        activity.setActive(true);

        try {
            databaseHelper.getActivityDao().update(activity);
            ActiveActivityListFragment activeActivityListFragment = getFragment(0);
            activeActivityListFragment.onActivityEnabled(activity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityDisabled(Activity activity) {
        activity.setActive(false);

        try {
            databaseHelper.getActivityDao().update(activity);
            ActiveActivityListFragment activeActivityListFragment = getFragment(0);
            activeActivityListFragment.onActivityDisabled(activity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityDeleted(Activity activity) {
        ActivityListFragment activityListFragment = getFragment(1);
        activityListFragment.onActivityDeleted(activity);
    }

    public <T extends Fragment> T getFragment (int index) {
        return (T)adapter.getItem(index);
    }
}
