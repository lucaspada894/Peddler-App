package com.coms309.peddler.Messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.coms309.peddler.Models.Message;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.GeneralAdapter;

import static com.coms309.peddler.utils.Const.WEBSOCKET_URL;

public class MessagePage extends AppCompatActivity implements View.OnClickListener {

    //Fields
    static ArrayList<Message> messagesFromUser = new ArrayList<>();
    static ArrayList<Message> messagesFromRecipient = new ArrayList<>();

    static String currentUserID;
    static String recipientUserID;

    private Button sendMsg;
    private EditText messageField;

    static String tag_json_arry = "jarray_req";

    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        sendMsg = findViewById(R.id.textSend);
        sendMsg.setOnClickListener(this);

        messageField = findViewById(R.id.enterText);

        Bundle extras = getIntent().getExtras();
        if (extras !=null) {
            this.setTitle(extras.getString("USER_NAME"));
            this.currentUserID = extras.getString("USER_ID");
            this.recipientUserID = extras.getString("REC_ID");
        }

        Log.d("message page:", "cur user id: [" + currentUserID + "], rec id: [" + recipientUserID + "]");
        makeJsonArryReq("/message/getBySender?creatorId=" + currentUserID, true);
        makeJsonArryReq("/message/getBySender?creatorId=" + recipientUserID, false);
        connect_to_socket();
    }

    public void connect_to_socket() {
        Draft[] drafts = {new Draft_6455()};
        String w = WEBSOCKET_URL;
        w += currentUserID;
        Log.d("websocket url", w);
        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textSend:
                cc.send(messageField.getText().toString());
                break;
        }
    }

    private void makeJsonArryReq(String path, final boolean currentUser) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        if (currentUser) {
                            messagesFromUser.clear();
                        } else {
                            messagesFromRecipient.clear();
                        }
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject msgObject = (JSONObject) response.get(i);
                                Log.d("response msg:", msgObject.toString());
                                if (currentUser) {
                                    messagesFromUser.add(new Message(msgObject.getString("creatorId"),
                                                                     msgObject.getString("recipientId"),
                                                                     msgObject.getString("message")));
                                } else {
                                    messagesFromRecipient.add(new Message(msgObject.getString("creatorId"),
                                                                          msgObject.getString("recipientId"),
                                                                          msgObject.getString("message")));
                                }
                            } catch (org.json.JSONException e) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("message page:", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve any messages", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    void filterMessages(ArrayList<Message> toRecMsg, ArrayList<Message> fromRecMsg) {
        for (int i = 0; i < toRecMsg.size(); i++) {
            if (!toRecMsg.get(i).getRecId().equals(recipientUserID)) {
                toRecMsg.remove(i);
                i = 0;
            }
        }
        for (int i = 0; i < fromRecMsg.size(); i++) {
            if (!fromRecMsg.get(i).getRecId().equals(currentUserID)) {
                fromRecMsg.remove(i);
                i = 0;
            }
        }
    }

    void connectToWebsocket() {
        Draft[] drafts = {new Draft_6455()};
        String w = WEBSOCKET_URL;
        w += currentUserID;
        Log.d("websocket url", w);
        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    if (message.toString().equals("SUCCESS")) {
                        makeJsonArryReq("/message/getBySender?creatorId=" + currentUserID, true);
                        makeJsonArryReq("/message/getBySender?creatorId=" + recipientUserID, false);
                    }
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
    }
}
