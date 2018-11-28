package com.coms309.peddler.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.coms309.peddler.Home.MenuPage;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageview;
    TextView su;
    EditText username, password;
    Button loginb, submitBtn;
    private ProgressDialog pDialog;

    private ArrayList<User> users = new ArrayList<>();

    //helper stuff
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    String TAG = "Log In";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.UsernameBox);
        password = findViewById(R.id.PasswordBox);

        //Login Button
        loginb = findViewById(R.id.LoginB);
        loginb.setOnClickListener(this);

        //Sign-up Text
        su = findViewById(R.id.SignupT);
        su.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(true);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            //Login implementation:
            case R.id.LoginB:
                if (validateForm()) {
                    Log.d("poop", username.getText().toString() + " " + password.getText().toString());
                    makeJsonArryReq("/user/all");
                }
                break;
            //Go to sign up page.
            case R.id.SignupT:
                pageSwitch(SignupActivity.class);
                break;
        }
        return;

    }

    private void makeJsonArryReq(String path) {
        //showProgressDialog();
        final JsonArrayRequest req = new JsonArrayRequest(Const.JSON_OBJECT_URL_SERVER + path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String id = "";
                        String email = "";
                        String firstName = "";
                        String lastName = "";
                        String password = "";
                        String phoneNumber = "";
                        String university = "";
                        String year = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = (JSONObject) response.get(i);
                                id = userObject.getString("id");
                                email = userObject.getString("email");
                                firstName = userObject.getString("firstName");
                                lastName = userObject.getString("lastName");
                                password = userObject.getString("password");
                                phoneNumber = userObject.getString("phoneNumber");
                                university = userObject.getString("university");
                                year = userObject.getString("year");
                                users.add(new User(id, email, firstName, lastName, password, phoneNumber, university, year));
                            } catch (org.json.JSONException e) {

                            }
                        }
                        for (int i = 0; i < users.size(); i++) {
                            Log.d(TAG, "onResponse: " + users.get(i).getEmail() + " id: " + users.get(i).getID() + "pass: " + users.get(i).getPassword());
                        }
                        users.add(new User("poop", "poop", "poop"));
                        if (users.size() > 0) {
                            attemptSignIn();
                            hideProgressDialog();
                        } else {
                            hideProgressDialog();
                            Toast.makeText(getApplicationContext(), "Failed to retrieve account credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
               // users.add(new User("poop", "poop", "poop", "poop"));
                if (users.size() > 0) {
                    attemptSignIn();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to retrieve account credentials", Toast.LENGTH_LONG).show();
                }
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), "Failed to retrieve account credentials", Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    private void attemptSignIn() {
        String currentEmail = username.getText().toString();
        String currentPass = password.getText().toString();
        Log.d("attempt sign in ", currentEmail + " " + currentPass);
        for (int i = 0; i < this.users.size(); i++) {
            String tempEmail = this.users.get(i).getEmail();
            String tempPass = this.users.get(i).getPassword();
            if (tempEmail.equals(currentEmail) && tempPass.equals(currentPass)) {
                Log.d(TAG, "attemptSignIn: success");
                pageSwitch(MenuPage.class);
                AppController.getInstance().CurrentUser = this.users.get(i);
            }
        }
    }

    private boolean validateForm() {
        int errors = 0;
        Log.d(TAG, "validateForm: \"" + username.getText() + "\"");
        if (username.getText().toString().equals("")) {
            errors += 1;
            username.setHint("enter first name");
            Log.d(TAG, "validateForm: firstname error");
            //username.setHintTextColor(getResources().getColor(R.color.colorError, getTheme()));
        }
        if (password.getText().toString().equals("")) {
            errors += 1;
            password.setHint("enter last name");
            //password.setHintTextColor(getResources().getColor(R.color.colorError, getTheme()));
        }
        return errors == 0;
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


    //Helper Method to switch between activities.
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }
}
