package com.agh.reminder.reminder.models;

import com.j256.ormlite.field.DatabaseField;

public class Configuration {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String key;
    @DatabaseField
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getKey () {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
