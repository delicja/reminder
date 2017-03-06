package com.agh.reminder.reminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agh.reminder.reminder.models.Report;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by emilia on 06.03.2017.
 */

public class CustomReportAdapter extends ArrayAdapter<Report> implements View.OnClickListener {

    private List<Report> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
    }

    public CustomReportAdapter(List<Report> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Report report = (Report) object;

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
        Report report = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;

        viewHolder.txtName.setText(report.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        viewHolder.txtType.setText(sdf.format(report.getDateFrom()));
        return convertView;
    }
}
