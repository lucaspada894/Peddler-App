package com.coms309.peddler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageview;
    TextView firstName, lastName, email, password;
    Button imageBtn, submitBtn;
    private ProgressDialog pDialog;

    private ArrayList<User> users = new ArrayList<>();

    //helper stuff
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    String TAG = "sign up";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.profile_image_btn:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            case R.id.submit_btn:
                //validateForm();
                makeJsonObjReq("/users/all");
        }
        return;
    }

    private void makeJsonObjReq(String path) {
        //showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.JSON_OBJECT_URL + path, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            String id = "";
                            String name = "";
                            String email = "";
                            String password = "";
                            JSONArray keys = response.names();
                            JSONArray values = response.toJSONArray(keys);
                            for(int i=0; i<values.length(); i++){
                                if (keys.getString(i).equals("id")){
                                    id = values.getString(i);
                                }
                                else if (keys.getString(i).equals("name")){
                                    name = values.getString(i);
                                }
                                else if (keys.getString(i).equals("email")){
                                    email = values.getString(i);
                                }
                                else if (keys.getString(i).equals("password")){
                                    password = values.getString(i);
                                }
                                users.add(new User(id, name, email, password));
                            }
                            for(int i=0; i<users.size(); i++){
                                Log.d(TAG, "user: " + users.get(i).getName());
                            }
                                Log.d(TAG, id + " " + name + " " + email + " " + password);
                        } catch (org.json.JSONException e) {
                            Log.d(TAG, "problem");
                        }
                        //hideProgressDialog();
                        attemptSignIn();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                System.out.println("error: " + error.getMessage());
                //hideProgressDialog();
            }
        });
//        {
//
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", "Androidhive");
//                params.put("email", "abc@androidhive.info");
//                params.put("pass", "password123");
//
//                return params;
//            }
//
//        };

        // Adding request to request queue
        Log.d("poop", jsonObjReq.getUrl());
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    private void attemptSignIn() {
        String currentEmail = email.getText().toString();
        String currentPass = password.getText().toString();
        for (int i = 0; i < this.users.size(); i++) {
            String tempEmail = this.users.get(i).getEmail();
            String tempPass = this.users.get(i).getPassword();
            if (tempEmail.equals(currentEmail) && tempPass.equals(currentPass)) {
                Log.d(TAG, "attemptSignIn: success");
            }
        }
    }

    private void validateForm() {
        int errors = 0;
        Log.d(TAG, "validateForm: \"" + firstName.getText() + "\"");
        if (firstName.getText().toString().equals("")) {
            errors += 1;
            firstName.setHint("enter first name");
            Log.d(TAG, "validateForm: firstname error");
            firstName.setHintTextColor(getResources().getColor(R.color.colorError, getTheme()));
        }
        if (lastName.getText().toString().equals("")) {
            errors += 1;
            lastName.setHint("enter last name");
            lastName.setHintTextColor(getResources().getColor(R.color.colorError, getTheme()));
        }
        if (email.getText().toString().equals("")) {
            errors += 1;
            email.setHint("enter email");
            email.setHintTextColor(getResources().getColor(R.color.colorError, getTheme()));
        }
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

}
