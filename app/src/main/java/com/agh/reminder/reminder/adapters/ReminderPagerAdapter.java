package com.agh.reminder.reminder.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.agh.reminder.reminder.fragments.ActivityListFragment;
import com.agh.reminder.reminder.fragments.ReportsFragment;

public class ReminderPagerAdapter extends FragmentStatePagerAdapter {

    public ReminderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActivityListFragment();
            case 1:
                return new ReportsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
