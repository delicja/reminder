package com.agh.reminder.reminder.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.agh.reminder.reminder.AddActivity;
import com.agh.reminder.reminder.MainActivity;
import com.agh.reminder.reminder.MultiTouchListener;
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

        databaseHelper = ((MainActivity) getActivity()).getDatabaseHelper();

        try {
            list = databaseHelper.getActivityDao().getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        activityList = (ListView) view.findViewById(R.id.activityList);
        adapter = new ActivitySelectorAdapter(list, getContext(), databaseHelper);
        adapter.setActivitySelectorHandler(parentActivity);
        activityList.setAdapter(adapter);

        setUpButton(view);

        return view;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);

        parentActivity = (IActivitySelectorHandler) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        parentActivity = (IActivitySelectorHandler) context;
    }

    public void onActivityDeleted(Activity activity) {
        int index = -1;
        for (Activity activityToRemove : list) {
            index++;
            if (activityToRemove.getId() == activity.getId()) {
                break;
            }
        }

        if (index >= 0) {
            list.remove(index);
        }
        adapter.notifyDataSetChanged();
    }

    private void setUpButton(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        ;
        params.setMargins(10, metrics.heightPixels * 72 / 96 - 150, 0, 0);
        fab.setLayoutParams(params);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), AddActivity.class);
                startActivity(myIntent);
            }
        });

        MultiTouchListener touchListener = new MultiTouchListener(getContext());
        fab.setOnTouchListener(touchListener);
    }
}
