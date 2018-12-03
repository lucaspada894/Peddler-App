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

public class PM extends AppCompatActivity implements View.OnClickListener {

    //Fields


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_pm);

        //Initializing
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.setTitle(extras.getString("USER_NAME"));

        }

        Log.d("PM", (String) this.getTitle());
    }

    @Override
    public void onClick(View v) {

    }
}