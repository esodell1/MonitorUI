package edu.uw.tcss450.monitorui.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import edu.uw.tcss450.monitorui.R;
import edu.uw.tcss450.monitorui.activities.DashboardActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 *
 * helper methods.
 */
public class MonitorSvc extends IntentService {
    private static final String TAG = "MonitorSvc";
    public static final String ACTION_START_SERVICE = "start_svc";
    public static final String ACTION_STOP_SERVICE = "stop_svc";

    protected static boolean SERVICE_RUNNING = false;

    public MonitorSvc() {
        super("MonitorService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(ACTION_START_SERVICE)) {
                startSvc();

            } else if (action.equals(ACTION_STOP_SERVICE)) {
                stopSvc();

            }
        }
    }

    private void startSvc() {
        SERVICE_RUNNING = true;
        Log.v(TAG, "Service started.");

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_sync_black_24dp);
        builder.setContentTitle("Monitor Service");
        builder.setContentText("Service is running.");

        Intent notificationIntent = new Intent(getApplicationContext(), DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        builder.setFullScreenIntent(pendingIntent, true);

        startForeground(1, builder.build());

    }

    private void stopSvc() {
        SERVICE_RUNNING = false;

    }


}
