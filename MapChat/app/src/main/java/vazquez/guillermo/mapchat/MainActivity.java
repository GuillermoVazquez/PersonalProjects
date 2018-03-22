package vazquez.guillermo.mapchat;

import android.app.FragmentManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vazquez.guillermo.mapchat.Fragments.MapFragment;
import vazquez.guillermo.mapchat.Fragments.UserListFragment;
import vazquez.guillermo.mapchat.MapChatObjects.LongiLat;
import vazquez.guillermo.mapchat.MapChatObjects.Person;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //instantiate all things fragments
    final FragmentManager fragmentManager = getFragmentManager();
    MapFragment mapFragment = new MapFragment();
    UserListFragment userListFragment = new UserListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //attach map at onCreate()
        fragmentManager.beginTransaction().add(R.id.attachTo,mapFragment).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.enterUsername){
            //now lets prompt the user for a userName
            final Toasty toasty = new Toasty(this);
            toasty.show();
            Button add = toasty.findViewById(R.id.addButton);
            Button cancel = toasty.findViewById(R.id.cancel);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = toasty.findViewById(R.id.stockToast);
                    String username = editText.getText().toString();
                    //set nav header userName
                    TextView userName = findViewById(R.id.userName);
                    userName.setText(username);
                    toasty.dismiss();
                    LongiLat longiLat = new LongiLat();
                    LatLng latLng = longiLat.getlongiLat(getApplicationContext(),getParent());
                    final Person person = new Person(username,latLng.longitude,latLng.latitude);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    // Define the POST request
                    String urlPost = "https://kamorris.com/lab/register_location.php";
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
                            System.out.println("---------"+person.getUserName());
                            params.put("user", person.getUserName());
                            params.put("longitude", Double.toString(person.getLong()));
                            params.put("latitude", Double.toString(person.getLat()));
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toasty.dismiss();
                }
            });
        }
        else if (id == R.id.nav_otherUsers){
            //display fragment showing list view of other users
            if(userListFragment.isAdded()){
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            else if(mapFragment.isAdded()){
                fragmentManager.beginTransaction().remove(mapFragment).commit();
                fragmentManager.beginTransaction().add(R.id.attachTo,userListFragment).commit();
            }
        }
        else if(id == R.id.map){
            //display the map
            if(mapFragment.isAdded()){
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            else if(userListFragment.isAdded()){
                fragmentManager.beginTransaction().remove(userListFragment).commit();
                fragmentManager.beginTransaction().add(R.id.attachTo,mapFragment).commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
