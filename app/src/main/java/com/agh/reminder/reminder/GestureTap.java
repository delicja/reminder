package com.agh.reminder.reminder;

import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureTap extends GestureDetector.SimpleOnGestureListener {

    private Context context;

    public GestureTap(Context context) {
        this.context = context;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Intent myIntent = new Intent(context, AddActivity.class);
        context.startActivity(myIntent);
        return true;
    }
}

