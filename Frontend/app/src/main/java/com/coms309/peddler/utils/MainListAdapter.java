package com.coms309.peddler.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coms309.peddler.Models.Project;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyViewHolder> {
    private ArrayList<Project> mDataset;
    private ArrayList<User> mDatasetUser;
    int type = -1;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView descText;
        public MyViewHolder(View v) {
            super(v);
            titleText = v.findViewById(R.id.item_name);
            descText = v.findViewById(R.id.item_desc);
        }
    }

    public MainListAdapter(ArrayList<Project> myDataset) {
        mDataset = myDataset;
    }
    public MainListAdapter(ArrayList<User> myDataset, int i) {
        mDatasetUser = myDataset;
        type = i;
    }

    @Override
    public MainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_row, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (this.type == -1) {
            holder.titleText.setText(mDataset.get(position).getName());
            holder.descText.setText(mDataset.get(position).getDesc());
        } else {
            holder.titleText.setText(mDatasetUser.get(position).getEmail());
            holder.descText.setText(mDatasetUser.get(position).getUniversity());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (type == -1) {
            return mDataset.size();
        } else {
            return mDatasetUser.size();
        }
    }
}
