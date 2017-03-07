package com.agh.reminder.reminder;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
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
    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
    }

    public CustomReportAdapter(List<Report> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.context = context;

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

        SpannableString description = new SpannableString(report.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SpannableString date = new SpannableString(sdf.format(report.getDateFrom()));
        if (!report.isRead()) {
            description.setSpan(new StyleSpan(Typeface.BOLD), 0, description.length(), 0);
            date.setSpan(new StyleSpan(Typeface.BOLD), 0, date.length(), 0);
        }
        viewHolder.txtName.setText(description);
        viewHolder.txtType.setText(date);

        return convertView;
    }

    
}
