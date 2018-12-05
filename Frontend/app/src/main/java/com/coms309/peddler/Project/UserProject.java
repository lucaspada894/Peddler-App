package com.coms309.peddler.Project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.coms309.peddler.Messages.GroupMessagePage;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.LessonAdapter;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.utils.MainListAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserProject extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText search;
    private Button psearch_btn, pcreate_btn, pmy_lessons;
    private ArrayList<Project> tutors = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner pfilter;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    //Fields
    private User CurrentUser;
    int[] icons = {R.drawable.less_icon};
    static ArrayList<String> convsMark = new ArrayList<>();
    static ArrayList<String> namesMark = new ArrayList<>();
    static ArrayList<User> usersMark = new ArrayList<>();
    static ArrayList<String> subjects = new ArrayList<>();
    static LessonAdapter adptMark;
    static boolean useFilter = true;
    ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_project);

        //Initializing
        update("/project/all");
        search = findViewById(R.id.psearch_text);
        psearch_btn = findViewById(R.id.pbutton);
        psearch_btn.setOnClickListener(this);

        pcreate_btn = findViewById(R.id.pcreate_lesson);
        pcreate_btn.setOnClickListener(this);

        pmy_lessons = findViewById(R.id.pmy_lessons);
        pmy_lessons.setOnClickListener(this);

        pfilter = (Spinner) findViewById(R.id.filter_projects);
        pfilter.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subjects));
        pfilter.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

        String selected = (String) parent.getItemAtPosition(pos);
        //Initialize
        clear();
        update("/project/search?major="+ useFilter + "&search=" + selected);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void update(String path) {
        makeJsonArryReq(path);

        userList = findViewById(R.id.main_recycle);
        adptMark = new LessonAdapter(UserProject.this, namesMark, convsMark, icons);
        userList.setAdapter(adptMark);
        if (AppController.getInstance().CurrentUser == null) {
            AppController.getInstance().CurrentUser = new User("-1000", "test", "test");
        }
        this.CurrentUser = AppController.getInstance().CurrentUser;
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pageSwitch(GroupMessagePage.class, usersMark.get(i));
            }
        });

    }

    public void clear() {
        convsMark.clear();
        namesMark.clear();
        usersMark.clear();
    }

    private void makeJsonArryReq(String path) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        String firstName = "";
                        String desc = "";
                        String subject = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("id");
                                firstName = userObject.getString("title");
                                desc = userObject.getString("description");
                                subject = userObject.getString("major");
                                namesMark.add(firstName);
                                convsMark.add(desc);
                                subjects.add(subject);
                                adptMark.notifyDataSetChanged();
                            } catch (org.json.JSONException e) {

                            }
                        }
                        for (int i = 0; i < usersMark.size(); i++) {
                            Log.d("friends:", "-" + usersMark.get(i).getFirstName() + " " + usersMark.get(i).getLastName() + ":" + usersMark.get(i).getID() + "-");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("friend list:", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve any projects", Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    //Helper Method to switch between activities.
    private void pageSwitch(Class obj, User temp) {
        Intent intent = new Intent(this, obj);
//        intent.putExtra("USER_NAME", temp.getFirstName() + " " + temp.getLastName());
//        intent.putExtra("USER_ID", CurrentUser.getID());
//        intent.putExtra("REC_ID", temp.getID());

        //makeJsonArryReq("/tutor/all");
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                String query = search.getText().toString();
                clear();
                update("/project/search/?search=" + query);
                break;

            case R.id.create_lesson:
            //    pageSwitch(CreateLesson.class);
                break;

            case R.id.my_lessons:
                clear();
                update("/project/myProjects/?userID=" + AppController.getInstance().CurrentUser.getID());

                break;
        }

    }


    //Helper
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }


}