package com.agh.reminder.reminder.custom.Interfaces;

import com.agh.reminder.reminder.models.Activity;

public interface IActivitySelectorHandler {
    void onActivityEnabled(Activity activity);
    void onActivityDisabled(Activity activity);
    void onActivityDeleted(Activity activity);
}
