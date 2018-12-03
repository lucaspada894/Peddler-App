package com.coms309.peddler.ProjectJobTutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;

import org.json.JSONObject;

import static com.coms309.peddler.utils.Const.JSON_OBJECT_URL_SERVER;

public class PostPage extends AppCompatActivity implements View.OnClickListener {

    //Private fields
    TextView Item_Name;
    TextView Item_Description;
    ImageView imageview;
    //post type 0=project, 1=lesson, 2=job.
    int postType = 0;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                postItem(postType);
                break;
        }
    }

    //Post new items.
    private void postItem(int type) {
        String addType;
        switch (type) {
            case 0:
                addType = "project";
                break;
            case 1:
                addType = "tutor";
                break;
            case 2:
                addType = "job";
                break;
            default:
                addType = "project";
                 break;
        }
        String url = JSON_OBJECT_URL_SERVER + "/project/add?";
        url = "http://proj309-pp-07.misc.iastate.edu:8080/" + addType + "/add";
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
