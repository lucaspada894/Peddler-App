package com.coms309.peddler;

import android.content.Intent;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coms309.peddler.ListItemActivity;
import com.coms309.peddler.SignupActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listItem = findViewById(R.id.list_item);
        listItem.setOnClickListener(this);

        Button signupBtn = findViewById(R.id.sign_up);
        signupBtn.setOnClickListener(this);

        Button requestPageBtn = findViewById(R.id.request_page);
        requestPageBtn.setOnClickListener(this);
    }


    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.list_item:
                Log.d("poop", "onClick: list item");
                startActivity(new Intent(MainActivity.this, ListItemActivity.class));
                break;
            case R.id.sign_up:
                Log.d("poop", "onClick: sign up");
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                break;
            case R.id.request_page:
                Log.d("poop", "onClick:  request page");
                startActivity(new Intent(MainActivity.this, JsonRequestActivity.class));
                break;
        }
        return;
    }
}
