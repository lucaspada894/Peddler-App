package com.coms309.peddler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.coms309.peddler.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import com.coms309.peddler.Models.User;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {

    //Fields
    private Button infosaveBtn;
    private EditText fnameBox, lnameBox, gradeBox, emailBox, phoneBox, currpassBox, passBox, passBox2;
    private User curr;
    private String tag_json_arry = "jarray_req", tag_string_req = "string_req";
    private String global_Password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //Initializing
        infosaveBtn = findViewById(R.id.InfosaveBtn);
        fnameBox = findViewById(R.id.FnameBox);
        lnameBox = findViewById(R.id.LnameBox);
        gradeBox = findViewById(R.id.GradeBox);
        emailBox = findViewById(R.id.EmailBox);
        phoneBox = findViewById(R.id.PhoneBox);
        currpassBox = findViewById(R.id.CurrPassBox);
        passBox = findViewById(R.id.PassBox);
        passBox2 = findViewById(R.id.PassBox2);
        curr = AppController.getInstance().CurrentUser;

        infosaveBtn.setOnClickListener(this);
        infoRequest();

    }


    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.InfosaveBtn:

                //Not changing password.
                if(currpassBox.getText().toString().equals("") && passBox.getText().toString().equals("")
                        && passBox2.getText().toString().equals("")) {

                    currpassBox.setText(global_Password);
                    passBox.setText(global_Password);
                    passBox2.setText(global_Password);

                    infoUpdate();
                    pageSwitch(MenuPage.class);
                    Toast.makeText(getApplicationContext(), "Updates Saved!", Toast.LENGTH_LONG).show();

                }
                else if(currpassBox.getText().toString().equals(curr.getPassword()) && passBox.getText().toString().equals(passBox2.getText().toString())) {

                    infoUpdate();
                    pageSwitch(MenuPage.class);
                    Toast.makeText(getApplicationContext(), "Updates Saved!", Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_LONG).show();

                }

                break;

        }

    }


    //Updates new user information
    private void infoUpdate() {

        String loginLink = "http://proj309-pp-07.misc.iastate.edu:8080/user/login?email=" + curr.getEmail() + "&password=" + curr.getPassword();
        String link = "http://proj309-pp-07.misc.iastate.edu:8080/user/";

        String[] updateList = {"editFirstName?newFirstName=" + fnameBox.getText().toString(), "editLastName?newLastName=" + lnameBox.getText().toString(),
                               "editYear?newYear=" + gradeBox.getText().toString(), "editEmail?newEmail=" + emailBox.getText().toString(),
                               "editPhoneNumber?newPhoneNumber=" + phoneBox.getText().toString(),
                               "editPassword?currentPassword=" + currpassBox.getText().toString() + "&newPassword=" + passBox.getText().toString() + "&newPassword2=" + passBox2.getText().toString()};

        //Login Request
        final StringRequest loginRequest = new StringRequest(Request.Method.GET, loginLink, new Response.Listener<String>(){

            public void onResponse(String str){

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(loginRequest, tag_string_req);



        //Updates Request
        for(int i = 0; i < updateList.length; i ++){

            final StringRequest updateRequest = new StringRequest(Request.Method.GET, link + updateList[i], new Response.Listener<String>(){

                public void onResponse(String str){

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.getMessage());
                }
            });
            AppController.getInstance().addToRequestQueue(updateRequest, tag_string_req);
            infoRequest();

        }

    }


    //Making Json-Request for account information
    private void infoRequest() {

        String link = "http://proj309-pp-07.misc.iastate.edu:8080/user/all";

        final JsonArrayRequest infoRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>(){

            public void onResponse(JSONArray r){

                for(int i = 0; i < r.length(); i ++){

                    try{

                        JSONObject temp = r.getJSONObject(i);

                        if(temp.getString("id").equals(curr.getID())){

                            fnameBox.setText(temp.getString("firstName"));
                            lnameBox.setText(temp.getString("lastName"));
                            gradeBox.setText(temp.getString("year"));
                            emailBox.setText(temp.getString("email"));
                            phoneBox.setText(temp.getString("phoneNumber"));
                            global_Password = temp.getString("password");
                            break;

                        }

                    }catch (org.json.JSONException e){

                    }

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("main page", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to retrieve account credentials", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(infoRequest, tag_json_arry);

    }


    //Helper
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }


}