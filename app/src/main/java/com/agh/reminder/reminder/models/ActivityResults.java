package com.agh.reminder.reminder.models;

import java.util.Date;

/**
 * Created by emilia on 05.03.2017.
 */

public class ActivityResults {

    private int id;
    private int activityId;
    private Date dateFrom;
    private Date dateTo;
    private double gpsDistance;
    private boolean notificationSent;
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
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
