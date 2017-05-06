package com.agh.reminder.reminder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.reminder.reminder.data_access.DatabaseHelper;
import com.agh.reminder.reminder.data_access.Interfaces.IActivityDao;
import com.agh.reminder.reminder.models.Activity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by emilia on 23.04.2017.
 */

public class CustomActivityAdapter extends ArrayAdapter<Activity> implements View.OnClickListener {

    private List<Activity> dataSet;
    private Context context;
    private DatabaseHelper databaseHelper;
    private IActivityDao activityDao;

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
                    //Toast.makeText(context, "Item was deleted!",Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, StartActivity.class);
                myIntent.putExtra("name", activity.getName());
                myIntent.putExtra("description", activity.getDescription());
                myIntent.putExtra("timeSpent", activity.getTime());
                myIntent.putExtra("id", activity.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);
            }
        });


        SpannableString name = new SpannableString(activity.getName());
        SpannableString description = new SpannableString(activity.getDescription());
        SpannableString timeSpent = new SpannableString(String.valueOf(activity.getTime()));
        viewHolder.txtName.setText(name);
        viewHolder.txtDescription.setText(description);
        viewHolder.txtTimeSpent.setText(timeSpent);

        return rowView;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }
}
