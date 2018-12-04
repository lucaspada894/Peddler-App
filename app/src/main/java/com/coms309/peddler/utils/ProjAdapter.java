package com.coms309.peddler.utils;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.coms309.peddler.R;


/**
 * ProjAdapter provides corresponding adaptation for MenuPage.
 */
public class ProjAdapter extends ArrayAdapter<String> {

    //Fields
    private ArrayList<String> names;
    private ArrayList<String> des;
    private Context currCtxt;


    //ViewContainer to optimize the repeated res look-up.
    static class ViewContainer {

        //Fields
        TextView na;
        TextView de;

    }


    //Initializing
    public ProjAdapter(Context context, ArrayList<String> n, ArrayList<String> d){

        super(context, R.layout.proj_row);
        names = n;
        des = d;
        currCtxt = context;

    }

    @Override
    public int getCount(){

        return names.size();

    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int pos, View targetV, ViewGroup parent){

        ViewContainer vc = new ViewContainer();

        if(targetV == null) {

            LayoutInflater currInfltr = (LayoutInflater) currCtxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            targetV = currInfltr.inflate(R.layout.proj_row, parent, false);

            //res look-up
            vc.na = (TextView) targetV.findViewById(R.id.projName);
            vc.de = (TextView) targetV.findViewById(R.id.projDes);
            targetV.setTag(vc);

        }
        else{

            vc = (ViewContainer) targetV.getTag();

        }
        vc.na.setText(names.get(pos));
        vc.de.setText(des.get(pos));

        return targetV;

    }


}