package com.coms309.peddler.Project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.MainListAdapter;
import com.coms309.peddler.utils.RecyclerItemClickListener;

public class FriendProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageView friendIcon;
    private User CurrentUser;
    private User displayUser;

    private TextView univ, year, number;
    private Button requestBtn, acceptBtn;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        CurrentUser = AppController.getInstance().CurrentUser;

        univ = findViewById(R.id.univ);
        year = findViewById(R.id.year);
        number = findViewById(R.id.number);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            displayUser = (User) extras.getSerializable("USER");
            this.setTitle(displayUser.getEmail());
            univ.setText("University: " + displayUser.getUniversity());
            year.setText("Year: " + displayUser.getYear());
            number.setText("Number: " + displayUser.getPhoneNumber());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
