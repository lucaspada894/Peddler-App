package com.coms309.peddler.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.coms309.peddler.Lesson.LessonPage;
import com.coms309.peddler.Market.MarketPage;
import com.coms309.peddler.Messages.FriendList;
import com.coms309.peddler.Messages.GroupMessagePage;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.Project.JoinableActivity;
import com.coms309.peddler.Project.UserProject;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.ProjAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MenuPage extends AppCompatActivity implements View.OnClickListener {

    private ImageView projBtn, messBtn, markBtn, lessBtn, persBtn, chatBtn;
    private ListView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private User CurrentUser;
    private ArrayList<Project> projects = new ArrayList<>();
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    static ProjAdapter adpt;
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<String> descriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        if (AppController.getInstance().CurrentUser == null) {
            AppController.getInstance().CurrentUser = new User("125", "test", "test");
        }
        this.CurrentUser = AppController.getInstance().CurrentUser;


        projBtn = findViewById(R.id.ProjBtn);
        messBtn = findViewById(R.id.MessBtn);
        lessBtn = findViewById(R.id.LessBtn);
        persBtn = findViewById(R.id.PersBtn);
        chatBtn = findViewById(R.id.Chat);
        markBtn = findViewById(R.id.Market);

        projBtn.setOnClickListener(this);
        messBtn.setOnClickListener(this);
        lessBtn.setOnClickListener(this);
        persBtn.setOnClickListener(this);
        chatBtn.setOnClickListener(this);
        markBtn.setOnClickListener(this);

        mRecyclerView = (ListView) findViewById(R.id.proj_list);
        adpt = new ProjAdapter(MenuPage.this, names, descriptions);
        mRecyclerView.setAdapter(adpt);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("selected project: ", projects.get(position).getName());
                getProjUsers(position);
            }
        });
        getProjects("/project/all");
        //updateUser("/user/all");
    }

    private void getProjUsers(final int position) {
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER +
                "/project/getProjectUsers?projectId=" + projects.get(position).getID(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<User> projUsers = new ArrayList<>();
                        String id = "";
                        String email = "";
                        Log.d("user list:", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("id");
                                email = userObject.getString("email");
                                projUsers.add(new User(id, email));
                            } catch (org.json.JSONException e) {

                            }
                        }
                        for (int i = 0; i < projUsers.size(); i++) {
                            Log.d("list users", "onResponse users: " + projUsers.get(i).getEmail() + " id: ");
                        }
                        projects.get(position).users = projUsers;
                        getProjRequests(position);
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
                                id = userObject.getString("userId");
                                projReq.add(new User(id));
                            } catch (org.json.JSONException e) {

                            }
                        }
                        for (int i = 0; i < projReq.size(); i++) {
                            Log.d("list users", "onResponse users: " + projReq.get(i).getID());
                        }
                        projects.get(position).requests = null;
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
                        names.clear();
                        descriptions.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObject = (JSONObject) response.get(i);
                                //Log.d("menu projects", responseObject.toString());
                                id = responseObject.getString("id");
                                name = responseObject.getString("title");
                                desc = responseObject.getString("description");
                                major = responseObject.getString("major");
                                ownerID = responseObject.getString("ownerID");
                                requesterID = responseObject.getString("requesterId");
                                ArrayList<User> projUsers = new ArrayList<>();
                                ArrayList<User> projRequest = new ArrayList<>();
                                projects.add(new Project(id, name, major, desc, ownerID, requesterID, projUsers, projRequest));
                                names.add(name);
                                descriptions.add(desc);
                                adpt.notifyDataSetChanged();
                            } catch (org.json.JSONException e) {

                            }
                        }
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

    private void updateUser(String path) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String projectID = "";
                        String year = "";
                        Log.d("update response: ", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                Log.d("update user:", userObject.toString());
                                String userId = userObject.getString("id");
                                projectID = userObject.getString("projectID");
                                if (CurrentUser.getID().equals(userId)) {
                                    CurrentUser.setProjectID(projectID);
                                    Log.d("new project id: ", CurrentUser.getProjectId());
                                }
                            } catch (org.json.JSONException e) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("menu update user", "Error: " + error.getMessage());
                // users.add(new User("poop", "poop", "poop", "poop"));
                Toast.makeText(getApplicationContext(), "Failed to update user", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.PersBtn:
                pageSwitch(ProfilePage.class);
                break;
            case R.id.ProjBtn:
                pageSwitch(UserProject.class);
                break;
            case R.id.MessBtn:
                pageSwitch(FriendList.class);
                break;
            case R.id.LessBtn:
                pageSwitch(LessonPage.class);
                break;
            case R.id.Chat:
                pageSwitch(GroupMessagePage.class);
                break;
            case R.id.Market:
                pageSwitch(MarketPage.class);
                break;
        }
    }

    private void pageSwitch(Class obj, Project temp) {
        Intent intent = new Intent(this, obj);
        intent.putExtra("PROJ", temp);
        startActivity(intent);
    }

    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }

}