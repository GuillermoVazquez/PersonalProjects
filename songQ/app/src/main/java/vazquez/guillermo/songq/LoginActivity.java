package vazquez.guillermo.songq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button start = findViewById(R.id.startParty);
        Button join = findViewById(R.id.joinParty);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the user wants to start a party
                //go to spotify oauth
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the user wants to join a party
                //skip Spotify oauth
            }
        });

    }
}
