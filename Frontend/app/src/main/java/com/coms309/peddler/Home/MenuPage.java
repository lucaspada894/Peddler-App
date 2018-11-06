package com.coms309.peddler.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.coms309.peddler.Messages.FirendList;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MenuPage extends AppCompatActivity implements View.OnClickListener {

    private ImageView projBtn, messBtn, markBtn, lessBtn, persBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private User CurrentUser;
    private ArrayList<Project> projects = new ArrayList<>();
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        //Initializing
        projBtn = findViewById(R.id.ProjBtn);
        messBtn = findViewById(R.id.MessBtn);
        markBtn = findViewById(R.id.MarkBtn);
        lessBtn = findViewById(R.id.LessBtn);
        persBtn = findViewById(R.id.PersBtn);

        projBtn.setOnClickListener(this);
        messBtn.setOnClickListener(this);
        markBtn.setOnClickListener(this);
        lessBtn.setOnClickListener(this);
        persBtn.setOnClickListener(this);

        this.CurrentUser = AppController.getInstance().CurrentUser;

        mRecyclerView = findViewById(R.id.proj_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        Log.d("user id", CurrentUser.getID());
        makeJsonArryReq("/project/all");

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.PersBtn:
                pageSwitch(ProfilePage.class);
                break;

            case R.id.ProjBtn:
                pageSwitch(MainActivity.class);
                break;

            case R.id.MessBtn:
                pageSwitch(FirendList.class);
                break;

            case R.id.MarkBtn:
                pageSwitch(MarketPage.class);
                break;

            case R.id.LessBtn:
                pageSwitch(LessonPage.class);
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


    //Helper
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }

}