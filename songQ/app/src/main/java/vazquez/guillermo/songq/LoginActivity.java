package vazquez.guillermo.songq;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vazquez.guillermo.songq.Fragments.JoinFragmnet;
import vazquez.guillermo.songq.Fragments.LoginFragment;

public class LoginActivity extends Activity implements LoginFragment.Communicate {

    protected FragmentManager fragmentManager = getFragmentManager();
    LoginFragment loginFragment = new LoginFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragmentManager.beginTransaction().replace(R.id.loginAttachTo,loginFragment)
        .addToBackStack(null).commit();

    }
}
