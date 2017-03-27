package com.agh.reminder.reminder.models;

/**
 * Created by emilia on 27.03.2017.
 */

public class Stopwatch {

    private int seconds;
    private int hours;
    private int minutes;
    private int sec;
    private boolean running;

    public void startStopwatch() {
        hours = seconds / 3600;
        minutes = (seconds % 3600) / 60;
        sec = seconds % 60;
        if (running) {
            seconds++;
        }
    }

    public void stopStopwatch() {
        running = false;
    }

    public void resumeStopwatch() {
        running = true;
    }

    public void resetStopwatch() {
        seconds = 0;
    }

    public String getHumanReadableTime() {
        return String.format("%02d:%02d:%02d", hours, minutes, sec);
    }

    public int getTime() {
        return seconds;
    }

}
