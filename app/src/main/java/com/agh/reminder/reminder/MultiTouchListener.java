package com.agh.reminder.reminder;

import android.content.Context;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MultiTouchListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;
    private int xDelta;
    private int yDelta;

    public MultiTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureTap(context));
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                        view.getLayoutParams();

                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;

                break;

            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();

                layoutParams.leftMargin = x - xDelta;
                layoutParams.topMargin = y - yDelta;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                view.setLayoutParams(layoutParams);

                break;
        }
        return true;
    }
}