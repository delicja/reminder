package com.agh.reminder.reminder.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.agh.reminder.reminder.AddActivity;
import com.agh.reminder.reminder.MainActivity;
import com.agh.reminder.reminder.R;
import com.agh.reminder.reminder.adapters.CustomActivityAdapter;
import com.agh.reminder.reminder.custom.Interfaces.IActivitySelectorHandler;
import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiveActivityListFragment extends Fragment {

    private List<Activity> list = new ArrayList<>();
    private static CustomActivityAdapter adapter;
    private ListView activityList;
    private IActivitySelectorHandler parentActivity;

    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        databaseHelper = ((MainActivity)getActivity()).getDatabaseHelper();
        databaseHelper.EnsureDefaultDataInitialized();

        try {
            list = databaseHelper.getActivityDao().getActive();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        activityList = (ListView) view.findViewById(R.id.activeActivityList);
        adapter = new CustomActivityAdapter(list, getContext());
        adapter.setActivitySelectorHandler(parentActivity);
        activityList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);

        parentActivity = (IActivitySelectorHandler)activity;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        parentActivity = (IActivitySelectorHandler)context;
    }

    public void onActivityEnabled(Activity activity) {
        list.add(activity);
        adapter.notifyDataSetChanged();
    }

    public void onActivityDisabled(Activity activity) {
        int index = -1;
        for (Activity activityToRemove : list) {
            index++;
            if(activityToRemove.getId() == activity.getId()) {
                break;
            }
        }

        if(index >= 0) {
            list.remove(index);
        }
        adapter.notifyDataSetChanged();
    }
}