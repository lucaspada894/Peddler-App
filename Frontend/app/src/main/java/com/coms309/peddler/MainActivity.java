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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.coms309.peddler.ListItemActivity;
import com.coms309.peddler.Models.Group;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.SignupActivity;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button listItem, signupBtn, requestPageBtn;

    private ArrayList<Project> projects = new ArrayList<>();

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
        //String data[] = {"poop1", "poop2", "poop3"};
        // specify an adapter (see also next example)
        makeJsonArryReq("/myProjects?userId=125");

        //postInfo();
    }

    private void makeJsonArryReq(String path) {
        //showProgressDialog();

        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        String name = "";
                        Log.d("response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("id");
                                name = userObject.getString("title");
                                projects.add(new Project(id, name));
                            } catch (org.json.JSONException e) {

                            }
                        }
                        mAdapter = new MainListAdapter(projects);
                        mRecyclerView.setAdapter(mAdapter);

                        mAdapter.notifyDataSetChanged();
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

    private void postInfo() {
        String url = JSON_OBJECT_URL_SERVER + "/user/add?";
        url = "http://proj309-pp-07.misc.iastate.edu:8080/project/add?";
        url += "title=" + "newprojtest" + "&major=" + "coms";
        url += "&description=" + "exdescription" + "&userID=" + 125 + "&ownerID=" + 125;
        final StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
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
