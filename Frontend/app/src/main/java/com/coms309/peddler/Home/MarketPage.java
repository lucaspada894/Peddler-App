package com.coms309.peddler.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.SearchView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.coms309.peddler.Messages.MessagePage;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.LessonAdapter;
import com.coms309.peddler.utils.MarketAdapter;
import com.coms309.peddler.Messages.FriendList;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarketPage extends AppCompatActivity implements View.OnClickListener {

    private EditText searchproText;
    private Button searchProduct, createProduct, myProducts;

    private ArrayList<Project> products = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    //Fields
    private User CurrentUser;

    int[] icons = {R.drawable.mark_icon};
    static ArrayList<String> convsMark = new ArrayList<>();
    static ArrayList<String> namesMark = new ArrayList<>();
    static ArrayList<User> usersMark = new ArrayList<>();
    static MarketAdapter adptMark;
    ListView marketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_page);

        //Initializing
        makeJsonArryReq("/product/all");
        searchproText = findViewById(R.id.searchpro_text);
        searchProduct = findViewById(R.id.search_product);
        searchProduct.setOnClickListener(this);

        createProduct = findViewById(R.id.create_product);
        createProduct.setOnClickListener(this);

        myProducts = findViewById(R.id.my_products);
        myProducts.setOnClickListener(this);

//        mRecyclerView = findViewById(R.id.lessons);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        marketList = findViewById(R.id.products);
        adptMark = new MarketAdapter(MarketPage.this, namesMark, convsMark, icons);
        marketList.setAdapter(adptMark);
        if (AppController.getInstance().CurrentUser == null) {
            AppController.getInstance().CurrentUser = new User("-1000", "test", "test");
        }
        this.CurrentUser = AppController.getInstance().CurrentUser;
        marketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pageSwitch(MessagePage.class, usersMark.get(i));
            }
        });
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
                Toast.makeText(getApplicationContext(), "Failed to retrieve any friends", Toast.LENGTH_LONG).show();
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
            case R.id.search_product:
                String query = searchproText.getText().toString();
                makeJsonArryReq("/product/search/?search=" + query);
                break;

            case R.id.create_product:
                pageSwitch(CreateProduct.class);
                break;

            case R.id.my_products:
                makeJsonArryReq("/product/myProducts/?userID=" + AppController.getInstance().CurrentUser.getID());
                break;
        }

    }

    private void makeJsonArryReq(String path, int i) {
        //showProgressDialog();

        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        String name = "";
                        String desc = "";
                        Log.d("response", response.toString());
                        products.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObject = (JSONObject) response.get(i);
                                id = responseObject.getString("userID");
                                name = responseObject.getString("tutorTitle");
                                desc = responseObject.getString("tutorSubject");
                                products.add(new Project(id, name, desc));
                            } catch (org.json.JSONException e) {

                            }
                        }
                        mAdapter = new MainListAdapter(products);
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