package com.agh.reminder.reminder.models;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class ActivityResults {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int activityId;
    @DatabaseField
    private Date date;
    @DatabaseField
    private double gpsDistance;
    @DatabaseField
    private boolean notificationSent;
    @DatabaseField
    private int timeSpent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date dateFrom) {
        this.date = dateFrom;
    }

    public double getGpsDistance() {
        return gpsDistance;
    }

    public void setGpsDistance(double gpsDistance) {
        this.gpsDistance = gpsDistance;
    }

    public boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }
}