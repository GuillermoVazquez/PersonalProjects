package vazquez.guillermo.mapchat.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import vazquez.guillermo.mapchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private GoogleMap googleMap;
    MapView mapView;
    View v;

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
                }
                else googleMap.setMyLocationEnabled(true);  // For showing a move to my location button

                //todo: below code for sydney... erase
                //todo: call LatLong to get current latLong of user and store in person object
                //todo: update LongiLat every 10ft or 30seconds
                    //todo: send updates here ( or where needed )

                // For dropping a marker at a point on the Map for current user
                LatLng user = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(user).title("Marker Title").snippet("Marker Description"));

                //todo: access server and get username + latlong of 12 nearby users (  by displayU )
                    //todo: store in-class Person objects
                //todo: drop pins for each user on map
                //todo: update displayU every 10ft or 30 seconds
                    //todo: send updates here ( or where needed )

                //camera
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(user).zoom(12).build();
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
