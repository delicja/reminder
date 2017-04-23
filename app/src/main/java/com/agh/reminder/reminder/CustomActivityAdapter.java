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
import android.widget.TextView;

import com.agh.reminder.reminder.models.Activity;

import java.util.List;

/**
 * Created by emilia on 23.04.2017.
 */

public class CustomActivityAdapter extends ArrayAdapter<Activity> implements View.OnClickListener {


    public CustomActivityAdapter(List<Activity> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.context = context;
    }

    private List<Activity> dataSet;
    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Activity activity = (Activity) object;

        Intent myIntent = new Intent(context, StartActivity.class);
        myIntent.putExtra("name", activity.getName());
        myIntent.putExtra("description", activity.getDescription());
        context.startActivity(myIntent);

        switch (v.getId()) {
            case R.id.name:
                //Toast.makeText(getContext(), report.getDescription(), Toast.LENGTH_LONG).show();


                break;
        }
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        Activity activity = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CustomActivityAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new CustomActivityAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomActivityAdapter.ViewHolder) convertView.getTag();
        }

        lastPosition = position;

        SpannableString name = new SpannableString(activity.getName());
        SpannableString description = new SpannableString(activity.getDescription());
        viewHolder.txtName.setText(name);
        viewHolder.txtType.setText(description);

        return convertView;
    }

}
