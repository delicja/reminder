package com.agh.reminder.reminder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.agh.reminder.reminder.R;
import com.agh.reminder.reminder.adapters.CustomActivityAdapter;
import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityListFragment extends Fragment {

    private List<Activity> list = new ArrayList<>();
    private static CustomActivityAdapter adapter;
    private ListView activityList;

    private DatabaseHelper _databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _databaseHelper = new DatabaseHelper(getContext());
        _databaseHelper.EnsureDefaultDataInitialized();

        try {
            list = _databaseHelper.getActivityDao().getActive();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        activityList = (ListView) getView().findViewById(R.id.activityList);
        adapter = new CustomActivityAdapter(list, getContext());
        activityList.setAdapter(adapter);
    }
}
