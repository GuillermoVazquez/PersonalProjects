package vazquez.guillermo.mapchat.MapChatObjects;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.android.volley.VolleyLog.TAG;

//to receive payloads
//to send upstream messages
//to receive notifications
public class MessagingService extends FirebaseMessagingService {

    //constructor
    public MessagingService() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //  handleNow();
            }

        }

        // Check if message contains a notification payload.
        //print out the downstream notification from server to test connection
        if (remoteMessage.getNotification() != null) {
            SharedPreferences sharedPreferences = getApplicationContext()
                    .getSharedPreferences("token_file",0);
            String defaultValue = "oops";
            final String token = sharedPreferences.getString("token", defaultValue);
            Log.d(TAG,"TOKEN: " + token);
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }
}
