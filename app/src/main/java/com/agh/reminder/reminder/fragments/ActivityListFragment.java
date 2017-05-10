package com.agh.reminder.reminder.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.agh.reminder.reminder.adapters.ActivitySelectorAdapter;
import com.agh.reminder.reminder.custom.Interfaces.IActivitySelectorHandler;
import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityListFragment extends Fragment {

    private List<Activity> list = new ArrayList<>();
    private ListView activityList;
    private ActivitySelectorAdapter adapter;
    private DatabaseHelper databaseHelper;

    private IActivitySelectorHandler parentActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        databaseHelper = ((MainActivity)getActivity()).getDatabaseHelper();

        try {
            list = databaseHelper.getActivityDao().getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        activityList = (ListView) view.findViewById(R.id.activityList);
        adapter = new ActivitySelectorAdapter(list, getContext(), databaseHelper);
        adapter.setActivitySelectorHandler(parentActivity);
        activityList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), AddActivity.class);
                startActivity(myIntent);
            }
        });

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

    public void onActivityDeleted(Activity activity) {
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
