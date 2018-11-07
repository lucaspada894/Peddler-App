package com.coms309.peddler.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.coms309.peddler.Messages.WebSocket;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;


public class UserProject extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private User CurrentUser;

    private Button listItem, webSocket;
    private EditText titleTxt, majorTxt, descTxt;

    private ArrayList<Project> projects = new ArrayList<>();

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItem = findViewById(R.id.list_item_btn);
        listItem.setOnClickListener(this);
        titleTxt = findViewById(R.id.title_text);
        majorTxt = findViewById(R.id.major_text);
        descTxt = findViewById(R.id.desc_text);

        mRecyclerView = findViewById(R.id.main_recycle);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (AppController.getInstance().CurrentUser == null) {
            AppController.getInstance().CurrentUser = new User("test", "test", "test");
        }
        this.CurrentUser = AppController.getInstance().CurrentUser;
        //Log.d("user id", CurrentUser.getID());
        makeJsonArryReq("/project/myProjects?userId=" + CurrentUser.getID());
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
                        String desc = "";
                        Log.d("response", response.toString());
                        projects.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObject = (JSONObject) response.get(i);
                                id = responseObject.getString("id");
                                name = responseObject.getString("title");
                                desc = responseObject.getString("description");
                                projects.add(new Project(id, name, desc));
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

    private void postInfo(String projTitle, String major, String desc) {
        String url = JSON_OBJECT_URL_SERVER + "/user/add?";
        url = "http://proj309-pp-07.misc.iastate.edu:8080/project/add?";
        url += "title=" + projTitle + "&major=" + major;
        url += "&description=" + desc + "&userID=" + CurrentUser.getID() + "&ownerID=" + CurrentUser.getID();
        final StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        makeJsonArryReq("/project/myProjects?userId=" + CurrentUser.getID());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_arry);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.list_item_btn:
                String title = titleTxt.getText().toString();
                String major = majorTxt.getText().toString();
                String desc = descTxt.getText().toString();
                if (title != null && major != null && desc != null){
                    postInfo(title, major, desc);
                }
//                Log.d("poop", "onClick: list item");
//                startActivity(new Intent(MainActivity.this, ListItemActivity.class));
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Enter new project name");
//
//                final EditText input = new EditText(this);
//                input.setInputType(InputType.TYPE_CLASS_TEXT);
//                builder.setView(input);
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        m_Text = input.getText().toString();
//                        postInfo(m_Text);
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.show();
                break;
        }
        return;
    }

    //Helper Method to switch between activities.
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }
}
