package com.coms309.peddler.Project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;

import java.util.ArrayList;

public class JoinableActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView projectIcon;
    private User CurrentUser;
    private Project currentProj;

    private TextView major, desc;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinable);

        major = findViewById(R.id.major_text);
        desc = findViewById(R.id.desc_text);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            currentProj = (Project) extras.getSerializable("PROJ");
            this.setTitle(currentProj.getName());
            major.setText(currentProj.getMajor());
            desc.setText(currentProj.getDesc());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //case
        }
    }
}
