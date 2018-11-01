package com.coms309.peddler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.GeneralAdapter;

public class FirendList extends AppCompatActivity {

    //Fields
    int[] icons = {R.drawable.pers_icon};
    static ArrayList<String> convs = new ArrayList<>();
    static ArrayList<String> names = new ArrayList<>();
    static GeneralAdapter adpt;
    static String tag_json_arry = "jarray_req";
    ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        //Initializing
        userRequest();
        convs.add("W'sup bitch");
        userList = (ListView) findViewById(R.id.listview);
        adpt = new GeneralAdapter(FirendList.this, names, convs, icons);
        userList.setAdapter(adpt);

    }

    //Request List of users
    private static void userRequest(){

        String link = "http://proj309-pp-07.misc.iastate.edu:8080/user/all";

        final JsonArrayRequest userReq = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray r) {

                try{

                    for(int i = 0; i < r.length(); i ++){

                        JSONObject jsobj = r.getJSONObject(i);

                        names.add(jsobj.getString("firstName") + " " + jsobj.getString("lastName"));
                        adpt.notifyDataSetChanged();

                    }

                }catch (org.json.JSONException e){

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


}