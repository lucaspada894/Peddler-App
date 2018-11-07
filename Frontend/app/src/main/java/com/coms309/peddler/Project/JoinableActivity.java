package com.coms309.peddler.Project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;

import java.util.ArrayList;

public class JoinableActivity extends AppCompatActivity {

    private ImageView projectIcon;
    private User CurrentUser;
    private Project currentProj;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
