package vazquez.guillermo.mapchat;

import android.app.DownloadManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vazquez.guillermo.mapchat.Fragments.UserListFragment;
import vazquez.guillermo.mapchat.MapChatObjects.Person;

import static android.content.ContentValues.TAG;

/**
 * Created by guillermo on 3/10/18.
 */

public class Connections {

    String urlPost = "https://kamorris.com/lab/register_location.php";
    ArrayList<String> partners;

    public ArrayList<String> getList(){
        return partners;
    }

    //POST
    //this POST will post the current user to the server
    public void postActionCurrentUser(Context context,final Person person){
        //sending a simple POST request
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlGet = "https://kamorris.com/lab/get_locations.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {
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
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",person.getUserName());
                params.put("longitude",Double.toString(person.getLong()));
                params.put("longitude",Double.toString(person.getLat()));

                return super.getParams();
            }
        };
        queue.add(postRequest);

    }

}