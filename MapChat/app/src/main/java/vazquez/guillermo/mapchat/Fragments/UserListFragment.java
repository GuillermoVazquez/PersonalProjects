package vazquez.guillermo.mapchat.Fragments;


import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import vazquez.guillermo.mapchat.Connections;
import vazquez.guillermo.mapchat.MainActivity;
import vazquez.guillermo.mapchat.MapChatObjects.Person;
import vazquez.guillermo.mapchat.R;

import static android.content.ContentValues.TAG;

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
                        Double longi = Double.parseDouble(partner.getString("longitude"));
                        Double lati = Double.parseDouble(partner.getString("latitude"));
                        //add Person object to partner list
                        arrayList.add(userName);
                    }
                    arrayAdapter.notifyDataSetChanged();
                    System.out.println(arrayList);

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
            }
        });


        return v;
    }

}
