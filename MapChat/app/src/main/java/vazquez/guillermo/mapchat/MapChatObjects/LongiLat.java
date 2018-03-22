package vazquez.guillermo.mapchat.MapChatObjects;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import vazquez.guillermo.mapchat.MainActivity;

/**
 * Created by guillermo on 3/11/18.
 */

public class LongiLat extends Service implements LocationListener {
    //this class will get the current long/lat coordinates of the user
    Location location;
    double longi;
    double lat;
    private Context context;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = (1000 * 60 * 1) / 2; // 30sec

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    //create class to get current longilat getLongiLat()
    public LatLng getlongiLat(Context context, Activity activity){

        //check for permissions
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            //get current longilat
            LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            LatLng user = new LatLng(latitude, longitude);
            return user;
        }
        return null;
    };

    //create class/thread to update every 10 ft or 30seconds longiLatUpdate

}
