package com.agh.reminder.reminder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agh.reminder.reminder.MainActivity;
import com.agh.reminder.reminder.R;
import com.agh.reminder.reminder.data_access.DatabaseHelper;


public class ReportsFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //databaseHelper = ((MainActivity)getActivity()).getDatabaseHelper();
        return inflater.inflate(R.layout.report, container, false);
    }
}
