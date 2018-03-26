package vazquez.guillermo.mapchat.MapChatObjects;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

import vazquez.guillermo.mapchat.R;

/**
 * Created by guillermo on 3/23/18.
 */

public class TokenStuff extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        //TODO: implement this to register in some way to server
        //sendTokenToServer(refreshToken);
    }


    private void sendTokenToServer(final String token){
        //get the username
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("username_file",0);
        String defaultValue = "G";
        final String username = sharedPreferences.getString("username", defaultValue);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Define the POST request
        String urlPost = "https://kamorris.com/lab/fcm_register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user", username);
                params.put("token", token);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
