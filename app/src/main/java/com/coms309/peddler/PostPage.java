package com.coms309.peddler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.coms309.peddler.Models.Project;
import com.coms309.peddler.app.AppController;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;

public class PostPage extends AppCompatActivity {

    //Private fields
    TextView Item_Name;
    TextView Item_Description;
    ImageView imageview;
    private String tag_json_obj = "jobj_req", tag_jsonproj_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        Button imageBtn = findViewById(R.id.image_btn);


        //Initializing fields
        Item_Name = findViewById(R.id.item_name);
        Item_Description = findViewById(R.id.item_description);
        imageview = findViewById(R.id.image_view);

    }

    public void onClick(View v)
    {

        switch(v.getId()){

            case R.id.image_btn:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
                break;





        }
        return;



//        if (v.getId() == R.id.image_btn) {
//            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(pickPhoto , 1);
//        }
//        return;
    }

    //Post new items.
    private void postItem() {

        String url = JSON_OBJECT_URL_SERVER + "/project/add?";
        url = "http://proj309-pp-07.misc.iastate.edu:8080/project/add";
        url += "?title=" + Item_Name.getText().toString() + "&major=" + Item_Name.getText().toString();
        url += "&description=" + Item_Description.getText().toString() + "&userID=" + 0;
        url += "&ownerID=" + 1;
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





















}
