package vazquez.guillermo.mapchat.MapChatObjects;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by guillermo on 3/11/18.
 */

public class OtherUsers {
    ArrayList<Person> orderedList = new ArrayList<Person>();

    public ArrayList<String> order(ArrayList<Person> partners, LatLng longiLat){
        ArrayList<String> returnList = new ArrayList<String>();
        //insertion sort
        for (int i = 1; i < partners.size(); i++){
            for(int j = i; j > 0; j--){
                if(getDistance(partners.get(j),longiLat) < getDistance(partners.get(j-1),longiLat)){
                    Person temp = partners.get(j);
                    partners.set(j,partners.get(j-1));
                    partners.set(j-1,temp);
                }
            }
        }
        for(int x = 0; x < partners.size(); x++){
            returnList.add(partners.get(x).getUserName());
            System.out.println(getDistance(partners.get(x),longiLat));
        }
        System.out.println(returnList);
        return returnList;
    }

    private double getDistance(Person person, LatLng longiLat){
        double distance = Math.sqrt(Math.pow(person.getLong() - longiLat.longitude,2) + Math.pow(person.getLat() - longiLat.latitude,2));
        return distance;
    }
}
