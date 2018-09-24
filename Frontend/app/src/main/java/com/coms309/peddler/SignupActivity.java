package com.coms309.peddler;

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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageview;

    TextView firstName, lastName, email;

    Button imageBtn, submitBtn;

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
        }
        return;
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
