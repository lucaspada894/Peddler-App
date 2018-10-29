package com.coms309.peddler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnCloseListener, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SearchView searchView;
    private User CurrentUser;

//    private Button listItem, signupBtn, requestPageBtn;

    private ArrayList<Project> projects = new ArrayList<>();

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_bar);

//        searchView.setActivated(true);
//        searchView.setQueryHint("Type your keyword here");
//        searchView.onActionViewExpanded();
//        searchView.setIconified(false);
//        searchView.clearFocus();
//        searchView.setOnCloseListener(this);
//        searchView.setOnQueryTextListener(this);
//        searchView.setOnSuggestionListener(this);

        //searchView
    }

    @Override
    public boolean onSuggestionClick(int i) {
        Log.d("Search activity:", "onSuggestionClick");
        return false;
    }

    @Override
    public boolean onSuggestionSelect(int i) {
        Log.d("Search activity:", "onSuggestionSelect");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d("Search activity:", "onQueryTextChange");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d("Search activity:", "onQueryTextSumbit");
        return false;
    }

    @Override
    public boolean onClose() {
        Log.d("Search activity:", "onClose");
        return false;
    }

    //Helper Method to switch between activities.
    private void pageSwitch(Class obj) {
        Intent intent = new Intent(this, obj);
        startActivity(intent);
    }
}
