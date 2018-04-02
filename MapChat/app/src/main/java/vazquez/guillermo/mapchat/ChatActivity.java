package vazquez.guillermo.mapchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

//import vazquez.guillermo.mapchat.MessageStuff.MessageListAdapter;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
   // private MessageListAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the intent
        Intent intent = getIntent();
        String partnerName = intent.getStringExtra("partnerName");
        Context context = this;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, partnerName, duration);
        toast.show();

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
       // mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

}
