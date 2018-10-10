package com.coms309.peddler.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coms309.peddler.Models.Project;
import com.coms309.peddler.R;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyViewHolder> {
    private ArrayList<Project> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleText;
        public TextView descText;
        public MyViewHolder(View v) {
            super(v);
            titleText = v.findViewById(R.id.item_name);
            descText = v.findViewById(R.id.item_desc);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainListAdapter(ArrayList<Project> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_row, parent, false);
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.main_list_row, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.titleText.setText(mDataset.get(position).getName());
        holder.descText.setText(mDataset.get(position).getDesc());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
