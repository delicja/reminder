package com.agh.reminder.reminder.models;

import com.j256.ormlite.field.DatabaseField;

public class Activity {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    //in seconds???
    @DatabaseField
    private int time;
    @DatabaseField
    private String description;
    @DatabaseField
    private boolean active;
    @DatabaseField
    private boolean needGps;
    @DatabaseField
    private boolean autoDetect;
    @DatabaseField
    private boolean isDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isNeedGps() {
        return needGps;
    }

    public void setNeedGps(boolean needGps) {
        this.needGps = needGps;
    }

    public boolean isAutoDetect() {
        return autoDetect;
    }

    public void setAutoDetect(boolean autoDetect) {
        this.autoDetect = autoDetect;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
