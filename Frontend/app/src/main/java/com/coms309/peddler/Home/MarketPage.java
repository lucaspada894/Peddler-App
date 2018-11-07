package com.coms309.peddler.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
<<<<<<< HEAD

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.coms309.peddler.Messages.FriendList;
import com.coms309.peddler.Messages.MessagePage;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MarketAdapter;
=======
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;
>>>>>>> ef045a19d6c591c86201c069d7c53794faa13912

public class MarketPage extends AppCompatActivity {

    //Fields
    private User CurrentUser;

<<<<<<< HEAD
    int[] icons = {R.drawable.mark_icon};
    static ArrayList<String> convsMark = new ArrayList<>();
    static ArrayList<String> namesMark = new ArrayList<>();
    static ArrayList<User> usersMark = new ArrayList<>();
    static MarketAdapter adptMark;
    ListView userList;
=======
public class MarketPage extends AppCompatActivity implements View.OnClickListener {


    private EditText search;
    private Button search_btn, create_btn, my_products;

    private ArrayList<Project> tutors = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
>>>>>>> ef045a19d6c591c86201c069d7c53794faa13912


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_page);

<<<<<<< HEAD
        //Initializing
        makeJsonArryReq("/product/all");
        userList = (ListView) findViewById(R.id.marklist);
        adptMark = new MarketAdapter(MarketPage.this, namesMark, convsMark, icons);
        userList.setAdapter(adptMark);
        if (AppController.getInstance().CurrentUser == null) {
            AppController.getInstance().CurrentUser = new User("-1000", "test", "test");
        }
        this.CurrentUser = AppController.getInstance().CurrentUser;
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pageSwitch(MessagePage.class, usersMark.get(i));
            }
        });
    }

    private void makeJsonArryReq(String path) {
        //showProgressDialog();
=======

        search = findViewById(R.id.search_text);
        search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);

        create_btn = findViewById(R.id.create_btn);
        create_btn.setOnClickListener(this);

        my_products = findViewById(R.id.my_products);
        my_products.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.products);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.search_btn:
                String query = search.getText().toString();
                makeJsonArryReq("/product/search/?search=" + query);
                break;

            case R.id.create_btn:
                pageSwitch(CreateProduct.class);
                break;

            case R.id.my_products:
                makeJsonArryReq("/product/myProducts/?userID=" + AppController.getInstance().CurrentUser.getID());
                break;
        }

    }





    private void makeJsonArryReq(String path) {
        //showProgressDialog();

>>>>>>> ef045a19d6c591c86201c069d7c53794faa13912
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
<<<<<<< HEAD
                        String firstName = "";
                        String desc = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("productID");
                                firstName = userObject.getString("productName");
                                desc = userObject.getString("productDescription");
                                usersMark.add(new User(firstName, desc, id, false));
                                namesMark.add(firstName);
                                convsMark.add(desc);
                                adptMark.notifyDataSetChanged();
=======
                        String name = "";
                        String desc = "";
                        Log.d("response", response.toString());
                        tutors.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObject = (JSONObject) response.get(i);
                                id = responseObject.getString("userID");
                                name = responseObject.getString("productName");
                                desc = responseObject.getString("productDescription");
                                tutors.add(new Project(id, name, desc));
>>>>>>> ef045a19d6c591c86201c069d7c53794faa13912
                            } catch (org.json.JSONException e) {

                            }
                        }
<<<<<<< HEAD
                        for (int i = 0; i < usersMark.size(); i++) {
                            Log.d("friends:", "-" + usersMark.get(i).getFirstName() + " " + usersMark.get(i).getLastName() + ":" + usersMark.get(i).getID() + "-");
                        }
=======
                        mAdapter = new MainListAdapter(tutors);
                        mRecyclerView.setAdapter(mAdapter);

                        mAdapter.notifyDataSetChanged();
>>>>>>> ef045a19d6c591c86201c069d7c53794faa13912
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
<<<<<<< HEAD
                Log.d("friend list:", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve any friends", Toast.LENGTH_LONG).show();
=======
                Log.d("main page", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve account credentials", Toast.LENGTH_LONG).show();
>>>>>>> ef045a19d6c591c86201c069d7c53794faa13912
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
<<<<<<< HEAD

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    //Helper Method to switch between activities.
    private void pageSwitch(Class obj, User temp) {
        Intent intent = new Intent(this, obj);
//        intent.putExtra("USER_NAME", temp.getFirstName() + " " + temp.getLastName());
//        intent.putExtra("USER_ID", CurrentUser.getID());
//        intent.putExtra("REC_ID", temp.getID());
        startActivity(intent);
    }


=======
    }


    //Helper
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }
>>>>>>> ef045a19d6c591c86201c069d7c53794faa13912
}