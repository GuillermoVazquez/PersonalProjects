package vazquez.guillermo.mapchat.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import vazquez.guillermo.mapchat.ChatActivity;
import vazquez.guillermo.mapchat.Connections;
import vazquez.guillermo.mapchat.MainActivity;
import vazquez.guillermo.mapchat.MapChatObjects.LongiLat;
import vazquez.guillermo.mapchat.MapChatObjects.OtherUsers;
import vazquez.guillermo.mapchat.MapChatObjects.Person;
import vazquez.guillermo.mapchat.R;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    View v;
    ArrayAdapter<String> arrayAdapter;


    public UserListFragment() {
        // Required empty public constructor
    }



    public static UserListFragment getInstance(){
        UserListFragment userListFragment = new UserListFragment();
        Bundle bundle = new Bundle();
        userListFragment.setArguments(bundle);
        return userListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ArrayList<Person> partners = new ArrayList<Person>();
        final LongiLat longiLat = new LongiLat();
        final OtherUsers otherUsers = new OtherUsers();
        //cordinates of current user
        LatLng latLng = longiLat.getlongiLat(getContext(),getActivity());
        v = inflater.inflate(R.layout.fragment_user_list,container,false);
        final ArrayList<String> arrayList = new ArrayList<String>();
        final ListView gridView= v.findViewById(R.id.listofusers);
        final Context context = ((MainActivity) getActivity());
        arrayAdapter = new ArrayAdapter<String>(context, R.layout.my_userlist_item, arrayList);
        gridView.setAdapter(arrayAdapter);

        //simple GET request
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
                        String userName = partner.getString("username");
                        try{
                            Double longi = Double.parseDouble(partner.getString("longitude"));
                            Double lati = Double.parseDouble(partner.getString("latitude"));
                            Person person = new Person(userName,longi,lati);
                            partners.add(person);
                        }catch (NumberFormatException e){};
                        try{

                        }catch (NumberFormatException e){};
                    }
                    //arrayList now has the partners
                    arrayList.addAll(otherUsers.order(partners,longiLat.getlongiLat(getContext(),getActivity())));
                    arrayAdapter.notifyDataSetChanged();

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

        //set click listeners for each user
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //here you will be able to chat with the nearby users after selection
                String partnerName = gridView.getItemAtPosition(i).toString();

                //check if user has partnerName public Key
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("partners",MODE_PRIVATE);
                //todo: get rid of !
                if(!sharedPreferences.contains(partnerName)){
                    //build intent
                    Intent intent = new Intent(getContext(),ChatActivity.class);
                    intent.putExtra("partnerName",partnerName);
                    //start activity
                    startActivity(intent);
                }else{
                    Context context = getContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Meet the other person first! ", duration);
                    toast.show();
                }
            }
        });


        return v;
    }

}
