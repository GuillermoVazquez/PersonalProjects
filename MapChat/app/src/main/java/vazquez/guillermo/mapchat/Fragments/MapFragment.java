package vazquez.guillermo.mapchat.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import vazquez.guillermo.mapchat.MainActivity;
import vazquez.guillermo.mapchat.MapChatObjects.LongiLat;
import vazquez.guillermo.mapchat.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private GoogleMap googleMap;
    MapView mapView;
    View v;
    LongiLat longiLat = new LongiLat();

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment getInstance(){
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_map, container, false);
        //generate MapView
        mapView =  v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        //to display the map immediately
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;

                //check for permissions
                if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                else googleMap.setMyLocationEnabled(true);  // For showing a move to my location button

                //todo: update LongiLat every 10ft or 30seconds
                    //todo: send updates here ( or where needed )

                // For dropping a marker at a point on the Map for current user
                LatLng user = longiLat.getlongiLat(getContext(),getActivity());

                googleMap.addMarker(new MarkerOptions().position(user).title("You").snippet(""));
                //simple GET request
                RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                String userName = partner.getString("username");
                                Double longi = Double.parseDouble(partner.getString("longitude"));
                                Double lati = Double.parseDouble(partner.getString("latitude"));
                                LatLng pos = new LatLng(lati, longi);
                                //add person to map
                                googleMap.addMarker(new MarkerOptions().position(pos).title(userName).snippet(""));

                            }

                        }catch (Exception e){
                            Log.e(TAG, "onResponse: ",e );}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ohhh shiiiii couldnt fetch the data...");
                    }
                });
                queue.add(stringRequest);


                //todo: access server and get username + latlong of 12 nearby users (  by displayU )
                    //todo: store in-class Person objects
                //todo: drop pins for each user on map
                //todo: update displayU every 10ft or 30 seconds
                    //todo: send updates here ( or where needed )
                //camera
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(user).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
