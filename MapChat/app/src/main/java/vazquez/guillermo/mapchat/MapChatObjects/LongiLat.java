package vazquez.guillermo.mapchat.MapChatObjects;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import vazquez.guillermo.mapchat.MainActivity;

/**
 * Created by guillermo on 3/11/18.
 */

public class LongiLat {
    //this class will get the current long/lat coordinates of the user

    double longi;
    double lat;

    //create class to get current longilat getLongiLat()
    public LatLng getlongiLat(Context context, Activity activity){

        //check for permissions
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        //get current longilat
        LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        LatLng user = new LatLng(latitude, longitude);

        return user;
    };

    //create class/thread to update every 10 ft or 30seconds longiLatUpdate

}
