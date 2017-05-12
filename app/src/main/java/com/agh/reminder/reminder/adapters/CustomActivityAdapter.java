package com.agh.reminder.reminder.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.agh.reminder.reminder.EditActivity;
import com.agh.reminder.reminder.R;
import com.agh.reminder.reminder.StartActivity;
import com.agh.reminder.reminder.custom.Interfaces.IActivitySelectorHandler;
import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;
import java.util.List;

public class CustomActivityAdapter extends ArrayAdapter<Activity> implements View.OnClickListener {

    private List<Activity> dataSet;
    private Context context;
    private DatabaseHelper databaseHelper;
    private IActivityDao activityDao;
    private IActivitySelectorHandler activitySelectorHandler;

    public CustomActivityAdapter(List<Activity> data, Context context) {
        super(context, 0, data);
        this.dataSet = data;
        this.context = context;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDescription;
        TextView txtTimeSpent;
    }

    @Override
    public void onClick(View v) {

    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Activity activity = getItem(position);
        final ViewHolder viewHolder;
        View rowView = convertView;

        if (rowView == null) {
            viewHolder = new ViewHolder();
            rowView = LayoutInflater.from(getContext()).inflate(R.layout.activity_row, parent, false);

            viewHolder.txtName = (TextView) rowView.findViewById(R.id.textName);
            viewHolder.txtDescription = (TextView) rowView.findViewById(R.id.textDescription);
            viewHolder.txtTimeSpent = (TextView) rowView.findViewById(R.id.textTimeSpent);

            databaseHelper = new DatabaseHelper(context);
            try {
                activityDao = databaseHelper.getActivityDao();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        lastPosition = position;
        //Handle buttons and add onClickListeners
        ImageButton editBtn = (ImageButton) rowView.findViewById(R.id.imageButton);
        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.imageButton2);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final int p = position;
                    dataSet.remove(p);
                    activityDao.deleteByID(activity.getId());
                    notifyDataSetChanged();

                    activitySelectorHandler.onActivityDeleted(activity);

                    //Toast.makeText(context, "Item was deleted!",Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity(activity, EditActivity.class);
                notifyDataSetChanged();
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity(activity, StartActivity.class);
            }
        });


        SpannableString name = new SpannableString(activity.getName());
        SpannableString description = new SpannableString(activity.getDescription());
        try {
            SpannableString timeSpent = new SpannableString(String.valueOf(databaseHelper.getActivityResultDao().countTimeSpentForToday(activity.getId())));
            viewHolder.txtTimeSpent.setText(timeSpent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        viewHolder.txtName.setText(name);
        viewHolder.txtDescription.setText(description);
        return rowView;
    }

    public void setActivitySelectorHandler(IActivitySelectorHandler activitySelectorHandler){
        this.activitySelectorHandler = activitySelectorHandler;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    private void goToNewActivity(Activity activity, Class<?> cls) {
        Intent myIntent = new Intent(context, cls);
        myIntent.putExtra("name", activity.getName());
        myIntent.putExtra("description", activity.getDescription());
        myIntent.putExtra("timeSpent", activity.getTime());
        myIntent.putExtra("id", activity.getId());
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}
