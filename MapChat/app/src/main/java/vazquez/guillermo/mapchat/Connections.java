package vazquez.guillermo.mapchat;

import android.content.Context;
import android.support.design.widget.Snackbar;

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

/**
 * Created by guillermo on 3/10/18.
 */

public class Connections {

    String urlPost = "https://kamorris.com/lab/register_location.php";

    //GET
    public void getActionUserList(Context context) {
        //sending a simple GET requestS
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlGet = "https://kamorris.com/lab/get_locations.php";
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, urlGet, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //this is where you receive the array
                try{
                    for (int i = 0; i < response.length(); i++){
                        //current json object
                        JSONObject partner = response.getJSONObject(i);

                        //get the current user
                        String userName = partner.getString("user");
                        Double longi = partner.getDouble("longitude");
                        Double lati = partner.getDouble("latitude");
                    }
                }catch (Exception e){e.printStackTrace();};


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ohhh shiiiii couldnt fetch the data...");
            }
        });
    }

    //POST
    public void postAction(){
        //sending a simple POST request

    }

}