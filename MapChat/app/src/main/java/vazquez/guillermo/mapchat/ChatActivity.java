package vazquez.guillermo.mapchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import vazquez.guillermo.mapchat.KeyStuff.Converters;
import vazquez.guillermo.mapchat.KeyStuff.Crypto;

//import vazquez.guillermo.mapchat.MessageStuff.MessageListAdapter;

public class ChatActivity extends AppCompatActivity {

    Converters converters = new Converters();
    Crypto crypto = new Crypto();

    private final String messagePostUrl = "https://kamorris.com/lab/send_message.php";
    String username = "G";
    private RecyclerView mMessageRecycler;
    SharedPreferences sp = getSharedPreferences("keys",0);
    //user private key
    String pk = sp.getString("private","");
    //privateKey String -> privateKey
    PrivateKey privateKey = converters.convertPrivate(pk);
    String sendMessageFinal;

    public ChatActivity() throws InvalidKeySpecException, NoSuchAlgorithmException {
    }
    //private MessageListAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the intent
        Intent intent = getIntent();
        final String partnerName = intent.getStringExtra("partnerName");
        //get partner public key
        final SharedPreferences sharedPreferences = this.getSharedPreferences("partners",MODE_PRIVATE);
        //the user hit send on a message
        final EditText editText = findViewById(R.id.edittext_chatbox);
        Button sendButton =findViewById(R.id.button_chatbox_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();
                //encrypt message first with partner public key
                try {

                    String partnerKey = sharedPreferences.getString(partnerName,"");
                    PublicKey partnerPublicKey = converters.convertPublic(partnerKey);
                    byte[] encryptedMessage = crypto.encryptString(partnerPublicKey,message);
                    String sendMessage = encryptedMessage.toString();
                    sendMessageFinal =sendMessage;
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Define the POST request
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String urlPost = messagePostUrl;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("to", partnerName);
                        params.put("from", username);
                        params.put("message",sendMessageFinal );
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });

        Context context = this;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, partnerName, duration);
        toast.show();

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
       // mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

}
