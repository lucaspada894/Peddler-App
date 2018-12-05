package com.coms309.peddler.Project;

import android.content.Intent;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;
import com.coms309.peddler.utils.Const;
import com.coms309.peddler.utils.MainListAdapter;
import com.coms309.peddler.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

public class JoinableActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView projectIcon;
    private User CurrentUser;
    private Project currentProj;

    private TextView owner, major, desc, memberLbl, requestLbl;
    private Button requestBtn, acceptBtn;
    private RecyclerView mRecyclerView, request;
    private LinearLayoutManager mLayoutManager, layoutM;
    private RecyclerView.Adapter mAdapter, sAdapter;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinable);

        owner = findViewById(R.id.owner_text);
        major = findViewById(R.id.major_text);
        desc = findViewById(R.id.desc_text);
        memberLbl = findViewById(R.id.member_lbl);
        requestLbl = findViewById(R.id.request_lbl);
        requestBtn = findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(this);
        acceptBtn = findViewById(R.id.acceptBtn);
        acceptBtn.setOnClickListener(this);
        acceptBtn.setAlpha((float) 0.0);

        Bundle extras = getIntent().getExtras();
        if (extras !=null) {
            currentProj = (Project) extras.getSerializable("PROJ");
            this.setTitle(currentProj.getName());
            major.setText("Major: " + currentProj.getMajor());
            desc.setText("Description: " + currentProj.getDesc());
            owner.setText("Owner: " + currentProj.getOwnerID());
        }

        CurrentUser = AppController.getInstance().CurrentUser;

        if (currentProj.getOwnerID().equals(CurrentUser.getID())) {
            requestBtn.setAlpha((float) 0.0);
            if (currentProj.requests.size() != 0) {
                acceptBtn.setAlpha((float) 1.0);
                acceptBtn.setText(currentProj.getRequestorId());
            }
        }

        mRecyclerView = findViewById(R.id.main_recycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainListAdapter(currentProj.getUsers(), 1);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        request = findViewById(R.id.request_list);
        request.setHasFixedSize(true);
        request.setLayoutManager(new LinearLayoutManager(this));
        sAdapter = new MainListAdapter(currentProj.getRequests(), 1);
        request.setAdapter(mAdapter);
        sAdapter.notifyDataSetChanged();

        if (currentProj.getRequests() == null) {
            requestLbl.setHeight(0);
        }
        if (currentProj.getUsers() == null) {
            memberLbl.setHeight(0);
        }
    }

    public void postProjectRequest() {
        Log.d("post request url: ", Const.SERVER_URL + "/project/sendRequest?requesterId=" + CurrentUser.getID() + "&projectId=" + currentProj.getID());
        final StringRequest postRequest = new StringRequest(Request.Method.GET, Const.SERVER_URL + "/project/sendRequest?requesterId=" + CurrentUser.getID() + "&projectId=" + currentProj.getID(),
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
    }

    public void acceptRequest() {
        Log.d("accept request url: ", Const.SERVER_URL + "/project/requestAction?requestStatus=" + true + "&projectId=" + currentProj.getID() + "&userId=" + currentProj.getRequestorId());
        final StringRequest postRequest = new StringRequest(Request.Method.GET, Const.SERVER_URL +
                "/project/requestAction?requestStatus=" + true + "&projectId=" + currentProj.getID() +
                "&userId=" + currentProj.getRequestorId()
                ,
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestBtn:
                postProjectRequest();
                break;
            case R.id.acceptBtn:
                acceptRequest();
                break;
        }
    }

    private void pageSwitch(Class obj, User u) {
        Intent intent = new Intent(this, obj);
        intent.putExtra("USER", u);
        startActivity(intent);
    }
}
