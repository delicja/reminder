package com.agh.reminder.reminder;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.agh.reminder.reminder.models.Stopwatch;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ActivityRecognizedService extends IntentService {

    private Stopwatch stopwatch;

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
        stopwatch = new Stopwatch();
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for (DetectedActivity activity : probableActivities) {
            switch (activity.getType()) {
                case DetectedActivity.WALKING: {
                    stopwatch.startStopwatch();
                    Log.e(ActivityRecognizedService.class.getName(), "Walking: " + activity.getConfidence());
                    if (activity.getConfidence() >= 35) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("Are you walking?");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                    }
                    break;
                }
                default:
                    stopwatch.stopStopwatch();
                    Toast.makeText(getApplicationContext(), stopwatch.getHumanReadableTime(),
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}
