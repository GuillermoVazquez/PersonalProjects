package vazquez.guillermo.mapchat.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.sql.Array;
import java.util.ArrayList;

import vazquez.guillermo.mapchat.MainActivity;
import vazquez.guillermo.mapchat.MapChatObjects.Person;
import vazquez.guillermo.mapchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    View v;
    ArrayList<Person> arrayList = new ArrayList<Person>();
    final Context context = ((MainActivity) getActivity());
    final ArrayAdapter<Person> arrayAdapter = new ArrayAdapter<Person>(context, android.R.layout.simple_list_item_1, arrayList);


    public UserListFragment() {
        // Required empty public constructor
    }

    public addToList()

    public static UserListFragment getInstance(String username,int longi, int lat){
        UserListFragment userListFragment = new UserListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        bundle.putInt("longi",longi);
        bundle.putInt("lat",lat);
        userListFragment.setArguments(bundle);
        return userListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_user_list,container,false);
        final ListView listView = v.findViewById(R.id.listofusers);
        listView.setAdapter(arrayAdapter);

        //set click listeners for each user
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //here you will be able to chat with the nearby users after selection
            }
        });


        return v;
    }

}
