package com.agh.reminder.reminder.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.fragments.ActiveActivityListFragment;
import com.agh.reminder.reminder.fragments.ActivityListFragment;
import com.agh.reminder.reminder.fragments.ReportsFragment;

public class ReminderPagerAdapter extends FragmentStatePagerAdapter {
    public ReminderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private ActiveActivityListFragment activeActivityListFragment;
    private ActivityListFragment activityListFragment;
    private ReportsFragment reportsFragment;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(activeActivityListFragment == null){
                    activeActivityListFragment = new ActiveActivityListFragment();
                }
                return activeActivityListFragment;
            case 1:
                if(activityListFragment == null){
                    activityListFragment = new ActivityListFragment();
                }
                return activityListFragment;
            case 2:
                if(reportsFragment == null){
                    reportsFragment = new ReportsFragment();
                }
                return reportsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
