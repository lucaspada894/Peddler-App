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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_signup);

        imageview = findViewById(R.id.image_view);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);

        imageBtn = findViewById(R.id.profile_image_btn);
        imageBtn.setOnClickListener(this);

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
                validateForm();
                makeJsonArryReq("/users/all");
        }
        return;
    }

    private void makeJsonArryReq(String path) {
        showProgressDialog();
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
                            Log.d(TAG, "onResponse: " + users.get(i).getFirstName());
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Error: " + error.getMessage());
                            //hideProgressDialog();
                        }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    private void attemptSignIn() {
        String currentEmail = email.getText().toString();
        String currentPass = password.getText().toString();
        for (int i = 0; i < this.users.size(); i++) {
            String tempEmail = this.users.get(i).getEmail();
            String tempPass = this.users.get(i).getPassword();
            if (tempEmail.equals(currentEmail) && tempPass.equals(tempPass)) {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                    imageBtn.setText("choose new photo");
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                    imageBtn.setText("choose new photo");
                }
                break;
        }
    }
}
