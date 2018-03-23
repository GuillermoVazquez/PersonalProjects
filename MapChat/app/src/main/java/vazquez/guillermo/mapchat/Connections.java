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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
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



    //POST
    //this POST will post the current user to the server
    public void postActionCurrentUser(Context context,final Person person){


    }

}