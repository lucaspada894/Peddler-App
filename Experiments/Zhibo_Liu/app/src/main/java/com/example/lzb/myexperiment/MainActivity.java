package com.example.lzb.myexperiment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String un;
    private String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login Button
        Button loginb = (Button) findViewById(R.id.LoginB);
        loginb.setOnClickListener(ClickEvents);

        //Sign-up Text
        TextView su = (TextView) findViewById(R.id.SignupT);
        su.setOnClickListener(ClickEvents);

        //Reference of Username and Password
        EditText username = (EditText) findViewById(R.id.UsernameBox);
        EditText password = (EditText) findViewById(R.id.PasswordBox);

        un = username.getText().toString();
        pw = password.getText().toString();

    }

    private View.OnClickListener ClickEvents = new View.OnClickListener() {

        @Override
        public void onClick(View v){

            switch(v.getId()) {

                case R.id.LoginB:

                    pageSwitch(ChatPage.class);

                    break;

                case R.id.SignupT:

                    pageSwitch(SignupPage.class);

                    break;

                default:

            }

        }


    };

    //Helper Method to switch between activities.
    private void pageSwitch(Class obj) {

        Intent intent = new Intent(this, obj);
        startActivity(intent);

    }








}
