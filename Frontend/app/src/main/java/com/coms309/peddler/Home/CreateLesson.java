package com.coms309.peddler.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;

import org.json.JSONObject;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;

public class CreateLesson extends AppCompatActivity implements View.OnClickListener{

    TextView subject, description, title, times;
    private Button create_btn;
    private String tag_json_obj = "jobj_req", tag_jsonproj_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subject = findViewById(R.id.subject_text);
        description = findViewById(R.id.description_text);
        title = findViewById(R.id.title_text);
        times = findViewById(R.id.time_text);

        create_btn = findViewById(R.id.create_btn);
        create_btn.setOnClickListener(this);


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    public void onClick(View v)
    {

        switch(v.getId()){

            case R.id.create_btn:
                postItem();
                pageSwitch(LessonPage.class);
                break;


        }
        return;

    }


    private void postItem() {

        String url;
        url = JSON_OBJECT_URL_SERVER + "/tutor/add";
        url += "?userID=" + AppController.getInstance().CurrentUser.getID() + "&tutorTitle=" + title.getText().toString();
        url += "&tutorSubject=" + subject.getText().toString() + "&tutorDescription=" + description.getText().toString();
        url += "&tutorTimes=" + times.getText().toString();
        JsonObjectRequest postRequest2 = new JsonObjectRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(postRequest2, tag_jsonproj_arry);

    }


    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }


}
