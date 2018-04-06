package vazquez.guillermo.mapchat.MapChatObjects;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import vazquez.guillermo.mapchat.KeyStuff.Converters;
import vazquez.guillermo.mapchat.KeyStuff.Crypto;

import static com.android.volley.VolleyLog.TAG;

//to receive payloads
//to send upstream messages
//to receive notifications
public class MessagingService extends FirebaseMessagingService {
    Converters converters = new Converters();
    Crypto crypto = new Crypto();
    SharedPreferences sharedPreferences = this.getSharedPreferences("partners",MODE_PRIVATE);
    SharedPreferences userSp = getSharedPreferences("keys", MODE_PRIVATE);
    String privK = userSp.getString("private","");
    PrivateKey privateKey = converters.convertPrivate(privK);



    //constructor
    public MessagingService() throws InvalidKeySpecException, NoSuchAlgorithmException {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            String jsonString = remoteMessage.getData().get("payload");
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                String partner = jsonObject.getString("from");
                //encrypted message received as string here in PEM format
                String partnerMessage = jsonObject.getString("message");
                byte[] message = partnerMessage.getBytes();
                message = crypto.decrypt(privateKey,message);
                //got the decrypted message
                String messageReceived = new String(message);

                //create broadcast to chatActivity
                Intent intent = new Intent("Got Message");
                intent.putExtra("message",messageReceived);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
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
