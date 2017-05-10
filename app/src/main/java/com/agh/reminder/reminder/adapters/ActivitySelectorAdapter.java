package com.agh.reminder.reminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.agh.reminder.reminder.R;
import com.agh.reminder.reminder.custom.Interfaces.IActivitySelectorHandler;
import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.models.Activity;

import java.util.List;

public class ActivitySelectorAdapter extends ArrayAdapter<Activity> {

    private List<Activity> dataSet;
    private Context context;
    private DatabaseHelper databaseHelper;
    private IActivitySelectorHandler activitySelectorHandler;

    private int lastPosition = -1;

    public ActivitySelectorAdapter(List<Activity> data, Context context, DatabaseHelper databaseHelper) {
        super(context, 0, data);
        this.dataSet = data;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    public void setActivitySelectorHandler(IActivitySelectorHandler activitySelectorHandler){
        this.activitySelectorHandler = activitySelectorHandler;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final Activity activity = getItem(position);
        final ActivitySelectorAdapter.ViewHolder viewHolder;
        View rowView = convertView;

        if (rowView == null) {
            viewHolder = new ActivitySelectorAdapter.ViewHolder();
            rowView = LayoutInflater.from(getContext()).inflate(R.layout.activity_selector_row, parent, false);

            viewHolder.txtName = (TextView) rowView.findViewById(R.id.activityNameTextView);
            viewHolder.switchActive = (Switch) rowView.findViewById(R.id.isActiveSwitch);

            viewHolder.switchActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked && !activity.isActive()){
                        activitySelectorHandler.onActivityEnabled(activity);
                    }else if(!isChecked && activity.isActive()){
                        activitySelectorHandler.onActivityDisabled(activity);
                    }
                }
            });

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ActivitySelectorAdapter.ViewHolder) rowView.getTag();
        }

        lastPosition = position;

        SpannableString name = new SpannableString(activity.getName());
        viewHolder.txtName.setText(name);
        viewHolder.switchActive.setChecked(activity.isActive());
        return rowView;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    private static class ViewHolder {
        TextView txtName;
        Switch switchActive;
    }
}
