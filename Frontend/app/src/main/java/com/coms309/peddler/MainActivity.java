package com.coms309.peddler;

import android.content.Intent;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.coms309.peddler.ListItemActivity;
import com.coms309.peddler.Models.Group;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.SignupActivity;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button listItem, signupBtn, requestPageBtn;

    private ArrayList<Group> projects = new ArrayList<>();

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItem = findViewById(R.id.list_item_btn);
        listItem.setOnClickListener(this);

        signupBtn = findViewById(R.id.sign_up);
        signupBtn.setOnClickListener(this);

        requestPageBtn = findViewById(R.id.request_page);
        requestPageBtn.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.main_recycle);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String data[] = {"poop1", "poop2", "poop3"};
        // specify an adapter (see also next example)
        mAdapter = new MainListAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    private void makeJsonArryReq(String path) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        String name = "";
                        String email = "";
                        String password = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("id");
                                name = userObject.getString("name");
                                email = userObject.getString("email");
                                password = userObject.getString("password");
                                projects.add(new Group(id, name, email));
                            } catch (org.json.JSONException e) {

                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("main page", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve account credentials", Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }


    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.list_item:
                Log.d("poop", "onClick: list item");
                startActivity(new Intent(MainActivity.this, ListItemActivity.class));
                break;
            case R.id.sign_up:
                Log.d("poop", "onClick: sign up");
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                break;
            case R.id.request_page:
                Log.d("poop", "onClick:  request page");
                startActivity(new Intent(MainActivity.this, JsonRequestActivity.class));
                break;
        }
        return;
    }
}
