package vazquez.guillermo.mapchat;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by guillermo on 3/7/18.
 */

public class Toasty extends Dialog {
    Activity activity;
    Dialog dialog;
    Button add;
    Button cancel;

    public Toasty(Activity activity){
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("signin/register");
        setContentView(R.layout.getting_username);
    }
}
