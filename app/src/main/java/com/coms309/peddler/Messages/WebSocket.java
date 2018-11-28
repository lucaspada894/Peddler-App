package com.coms309.peddler.Messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;
import static com.coms309.peddler.utils.Const.WEBSOCKET_URL;

public class WebSocket extends AppCompatActivity implements View.OnClickListener {

    Button  b1,b2;
    EditText e1,e2;
    TextView t1;

    private User CurrentUser;


    private WebSocketClient cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        b2=(Button)findViewById(R.id.bt2);
        e2=(EditText)findViewById(R.id.et2);
        t1=(TextView)findViewById(R.id.tx1);

        this.CurrentUser = AppController.getInstance().CurrentUser;
        b2.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt2:
                Draft[] drafts = {new Draft_6455()};
                String w = WEBSOCKET_URL;
                w += CurrentUser.getID();
                Log.d("websocket url", w);
                try {
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                        @Override
                        public void onMessage(String message) {
                            Log.d("", "run() returned: " + message);
                            String s = t1.getText().toString();
                            t1.setText("Server:" + message + "\n" + s);
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
}
