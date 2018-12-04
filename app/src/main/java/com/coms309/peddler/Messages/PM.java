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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android.volley.toolbox.StringRequest;
import com.coms309.peddler.Home.MenuPage;
import com.coms309.peddler.Models.Message;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.Project.JoinableActivity;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.ChatAdapter;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.GeneralAdapter;
import com.coms309.peddler.utils.MainListAdapter;
import com.coms309.peddler.utils.ProjAdapter;
import com.coms309.peddler.utils.RecyclerItemClickListener;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;
import static com.coms309.peddler.utils.Const.WEBSOCKET_URL;

public class PM extends AppCompatActivity implements View.OnClickListener {

    //Fields
    private ArrayList<Message> messages = new ArrayList<>();

    private ListView mRecyclerView;
    private GeneralAdapter adpt;
    private EditText messBox;
    private Button sendBtn;

    private User convoBuddy = new User("12");
    private User CurrentUser = AppController.getInstance().CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_pm);

        messBox = findViewById(R.id.enterText);
        sendBtn = findViewById(R.id.textSend);
        sendBtn.setOnClickListener(this);

        //Initializing
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.setTitle(extras.getString("USER_NAME"));
            //convoBuddy = (User) extras.get("CONVO");
        }

        mRecyclerView = findViewById(R.id.main_recycle);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView = (ListView) findViewById(R.id.proj_list);

        Log.d("PM", (String) this.getTitle());
        getMessagesPath("/message/all");
    }

    private void getMessagesPath(String path) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        String creatorID = "";
                        String recipientID = "";
                        String time = "";
                        String msg = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObject = (JSONObject) response.get(i);
                                Log.d("response adsfa", responseObject.toString());
                                id = responseObject.getString("id");
                                creatorID = responseObject.getString("creatorId");
                                recipientID = responseObject.getString("recipientId");
                                time = responseObject.getString("date");
                                msg = responseObject.getString("messageBody");
                                boolean sent = creatorID.equals(AppController.getInstance().CurrentUser.getID());
                                Log.d("buddy", convoBuddy.getID());
                                if (recipientID.equals(convoBuddy.getID()) && creatorID.equals(CurrentUser.getID()) || recipientID.equals(CurrentUser.getID()) && creatorID.equals(convoBuddy.getID())) {
                                    messages.add(new Message(creatorID, recipientID, msg, time, sent));
                                }
                            } catch (org.json.JSONException e) {
                                System.out.println("e: " + e);
                            }
                        }
                        Log.d("messages", Arrays.toString(messages.toArray()));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("main page", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve project from proj table", Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, "12");
    }

    private void sendMsg(String msg) {
        String url = JSON_OBJECT_URL_SERVER + "/user/add?";
        url = "http://proj309-pp-07.misc.iastate.edu:8080/message/add?";
        url += "creatorId=" + CurrentUser.getID() + "&recipientId=" + convoBuddy.getID() +
                "&message=" + msg;
        final StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        getMessagesPath("/message/all");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(postRequest, "13");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textSend:
                sendMsg(messBox.getText().toString());
                break;
        }
    }
}