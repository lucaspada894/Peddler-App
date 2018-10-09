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
import com.android.volley.toolbox.StringRequest;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageview;
    TextView firstName, lastName, email, phoneNumber, year, password;
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
        phoneNumber = findViewById(R.id.phoneN);
        year = findViewById(R.id.grade);
        password = findViewById(R.id.pass);

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
                //validateForm();
                postInfo("email", "password");
        }
        return;
    }

    private void postInfo(String email, String password) {
        String url = JSON_OBJECT_URL_SERVER + "/user/add?";
        url = "http://proj309-pp-07.misc.iastate.edu:8080/user/add";
        url += "?firstName=" + firstName.getText().toString() + "&lastName=" + lastName.getText().toString();
        url += "&email=" + this.email.getText().toString() + "&phoneNumber=" + phoneNumber.getText().toString();
        url += "&year=" + year.getText().toString() + "&university=ISU&password=" + this.password.getText().toString();
        final StringRequest postRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response", response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.getMessage());
                }
            });
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_arry);

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
