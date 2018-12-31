package si.smartspent.smartspent.Notifications;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationsIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_START = "si.smartspent.smartspent.action.START";
    private static final String ACTION_DELETE = "si.smartspent.smartspent.action.DELETE";

    private static final String CHANNEL_ID = "1";
    private static final String CHANNEL_NAME = "Business";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "si.smartspent.smartspent.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "si.smartspent.smartspent.extra.PARAM2";

    public NotificationsIntentService() {
        super("NotificationsIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationsIntentService.class);
        intent.setAction(ACTION_START);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Delete. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationsIntentService.class);
        intent.setAction(ACTION_DELETE);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch(action) {
                case ACTION_START:
                    handleActionStart(intent.getStringExtra(EXTRA_PARAM1), intent.getStringExtra(EXTRA_PARAM2));
                    break;
                case ACTION_DELETE:
                    handleActionDelete(intent.getStringExtra(EXTRA_PARAM1), intent.getStringExtra(EXTRA_PARAM2));
                    break;
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStart(String param1, String param2) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        } else {

        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDelete(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
