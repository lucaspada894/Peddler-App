package com.coms309.peddler.Project;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.Project.JoinableActivity;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;
import com.coms309.peddler.utils.RecyclerItemClickListener;

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
        setContentView(R.layout.activity_user_project);
        this.CurrentUser = AppController.getInstance().CurrentUser;
        Log.d("userproject curr user:", CurrentUser.getID());

        listItem = findViewById(R.id.list_item_btn);
        listItem.setOnClickListener(this);
        titleTxt = findViewById(R.id.title_text);
        majorTxt = findViewById(R.id.major_text);
        descTxt = findViewById(R.id.desc_text);

        mRecyclerView = findViewById(R.id.main_recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                Log.d("pooooop", projects.get(position).getDesc());
                getProjRequests(position);
            }
            @Override public void onLongItemClick(View view, int position) { }
        }));
        getProjects("/project/myProjects?userId=" + CurrentUser.getID());
        //getProjById("/project/fetchProject?projectId=" + CurrentUser.getProjectId());
    }

    private void getProjects(String path) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        String name = "";
                        String desc = "";
                        String major = "";
                        String ownerID = "";
                        String requesterID = "";
                        JSONArray users;
                        JSONArray requests;
                        projects.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObject = (JSONObject) response.get(i);
                                Log.d("response user project", responseObject.toString());
                                id = responseObject.getString("id");
                                name = responseObject.getString("title");
                                desc = responseObject.getString("description");
                                major = responseObject.getString("major");
                                ownerID = responseObject.getString("ownerID");
                                requesterID = responseObject.getString("requesterId");
//                                users = responseObject.getJSONArray("users");
//                                requests = responseObject.getJSONArray("requestes");
                                ArrayList<User> projUsers = new ArrayList<>();
                                ArrayList<User> projRequest = new ArrayList<>();
//                                for (int k = 0; i < (users.length() > requests.length() ? users.length() : requests.length()); i++) {
//                                    if (!(k >= users.length())) {
//                                        Log.d("user:", users.get(k).toString());
//                                    }
//                                    if (!(k >= requests.length())) {
//                                        Log.d("requests:", requests.get(k).toString());
//                                    }
//                                }
                                projects.add(new Project(id, name, major, desc, ownerID, requesterID, projUsers, projRequest));
                            } catch (org.json.JSONException e) {
                                System.out.println("e: " + e);
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
                Toast.makeText(getApplicationContext(), "Failed to retrieve project from proj table", Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    private void getProjRequests(final int position) {
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER +
                "/project/fetchProjectRequests?projectId=" + projects.get(position).getID(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<User> projReq = new ArrayList<>();
                        String id = "";
                        String email = "";
                        Log.d("request list:", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("id");
                                projReq.add(new User(id));
                            } catch (org.json.JSONException e) {

                            }
                        }
                        for (int i = 0; i < projReq.size(); i++) {
                            Log.d("list users", "onResponse users: " + projReq.get(i).getEmail() + " id: ");
                        }
                        projects.get(position).requests = projReq;
                        pageSwitch(JoinableActivity.class, projects.get(position));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("menu error: ", error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve projects", Toast.LENGTH_LONG).show();
            }
        });
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
                        getProjects("/project/myProjects?userId=" + CurrentUser.getID());
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
                } else {
                    titleTxt.setHint("Enter a title");
                    majorTxt.setHint("Enter a Major");
                    descTxt.setHint("Enter a Description");
                }
                break;
        }
        return;
    }

    //Helper Method to switch between activities.
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }

    private void pageSwitch(Class obj, Project proj) {
        Intent intent = new Intent(this, obj);
        intent.putExtra("PROJ", proj);
        startActivity(intent);
    }
}
