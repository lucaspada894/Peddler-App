package com.coms309.peddler.Messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.coms309.peddler.Models.Message;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.ChatAdapter;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.GeneralAdapter;

import static com.coms309.peddler.utils.Const.WEBSOCKET_URL;

public class MessagePage extends AppCompatActivity implements View.OnClickListener {

    //Fields
    static ArrayList<String> messages = new ArrayList<>();
    static String currentUserID;
    static String recipientUserID;
    static String tag_json_arry = "jarray_req";
    private ArrayList<Message> messageList = new ArrayList<>();
    private RecyclerView mMessageRecycler;
    private ChatAdapter mMessageAdapter;
    private Button sendBtn;
    private EditText messBox;
    private User currUser;
    private WebSocketClient cc;
    private Message tempRei;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //Initializing
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            this.setTitle(extras.getString("USER_NAME"));
            this.currentUserID = extras.getString("USER_ID");
            this.recipientUserID = extras.getString("REC_ID");
        }

        currUser = AppController.getInstance().CurrentUser;
        messBox = findViewById(R.id.enterText);
        sendBtn = findViewById(R.id.textSend);
        sendBtn.setOnClickListener(this);

//        Message testMess = new Message("testSenderID","testRecID", "Hello, welcome to Peddler!");
//        messageList.add(testMess);

//        Log.d("message page:", "cur user id: [" + currentUserID + "], rec id: [" + recipientUserID + "]");
//        makeJsonArryReq("/message/getBySender?creatorId=" + currentUserID);
//        makeJsonArryReq("/message/getBySender?creatorId=" + recipientUserID);

        mMessageRecycler = (RecyclerView) findViewById(R.id.messList);
        mMessageAdapter = new ChatAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setHasFixedSize(true);
        mMessageRecycler.setAdapter(mMessageAdapter);


    }

    //Send Text -> Websocket
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.textSend:

//                //WebSocket Communication (Client<->Server).
                Draft[] drafts = {new Draft_6455()};
                String w = WEBSOCKET_URL;
                w += currUser.getID();

                Log.d("websocket url", w);
                try {

                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                        @Override
                        public void onMessage(String message) {

                            Log.d("", "run() returned: " + message);

                            //Update sent texts
                            String sent_s = messBox.getText().toString();
                            Message sent_m = new Message("userID","testRecID", sent_s);
                            messageList.add(sent_m);

                            //Update received texts
                            Message temp = new Message("testSenderID","testRecID", "Server: " + message);
                            messageList.add(temp);

                            mMessageAdapter.notifyItemRangeInserted(messageList.size() == 0? 0 :messageList.size()  - 2, 2);

                        }

                        @Override
                        public void onOpen(ServerHandshake handshake) {

                            Log.d("OPEN", "run() returned: " + "is connecting");

                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {

                            Log.d("CLOSE", "onClose() returned: " + reason);

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }
                cc.connect();

                break;

        }





    }





    private void makeJsonArryReq(String path) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                Log.d("response msg:", userObject.toString());







                            } catch (org.json.JSONException e) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("message page:", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve any friends", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }
}
