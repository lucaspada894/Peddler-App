package com.coms309.peddler.Messages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.GeneralAdapter;

public class FriendList extends AppCompatActivity {

    //Fields
    private User CurrentUser;

    int[] icons = {R.drawable.pers_icon};
    static ArrayList<String> convs = new ArrayList<>();
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static GeneralAdapter adpt;
    ListView userList;

    static String tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        //Initializing
        makeJsonArryReq("/user/all");
        convs.add("W'sup bitch");
        userList = (ListView) findViewById(R.id.frilist);
        adpt = new GeneralAdapter(FriendList.this, names, convs, icons);
        userList.setAdapter(adpt);
        if (AppController.getInstance().CurrentUser == null) {
            AppController.getInstance().CurrentUser = new User("-1000", "test", "test");
        }
        this.CurrentUser = AppController.getInstance().CurrentUser;
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pageSwitch(MessagePage.class, users.get(i));
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
                        String lastName = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("id");
                                firstName = userObject.getString("firstName");
                                lastName = userObject.getString("lastName");
                                users.add(new User(firstName, lastName, id, false));
                                names.add(firstName + " " + lastName);
                                adpt.notifyDataSetChanged();
                            } catch (org.json.JSONException e) {

                            }
                        }
                        for (int i = 0; i < users.size(); i++) {
                            Log.d("friends:", "-" + users.get(i).getFirstName() + " " + users.get(i).getLastName() + ":" + users.get(i).getID() + "-");
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

    //Request List of users
    private static void userRequest(){
        String link = "http://proj309-pp-07.misc.iastate.edu:8080/user/all";

        final JsonArrayRequest userReq = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray r) {
                try {
                    for(int i = 0; i < r.length(); i ++){

                        JSONObject jsobj = r.getJSONObject(i);

                        names.add(jsobj.getString("firstName") + " " + jsobj.getString("lastName"));
                        adpt.notifyDataSetChanged();
                    }
                } catch (org.json.JSONException e) {

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("main page", "Error: " + error.getMessage());
            }

        });

        AppController.getInstance().addToRequestQueue(userReq, tag_json_arry);
    }

    //Helper Method to switch between activities.
    private void pageSwitch(Class obj, User temp) {
        Intent intent = new Intent(this, obj);
        intent.putExtra("USER_NAME", temp.getFirstName() + " " + temp.getLastName());
        intent.putExtra("USER_ID", CurrentUser.getID());
        intent.putExtra("REC_ID", temp.getID());
        startActivity(intent);
    }
}