package vazquez.guillermo.songq.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vazquez.guillermo.songq.MainActivity;
import vazquez.guillermo.songq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    Communicate communicate;

    Button start;
    Button join;
    View v;

    public LoginFragment() {
        // Required empty public constructor
    }

    public interface Communicate{

        public void onStart();

        public void onJoin();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);
        start = v.findViewById(R.id.startParty);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //have the user start a party
                Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        join = v.findViewById(R.id.joinParty);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //have the user join a party


            }
        });


        return v;

    }

}
