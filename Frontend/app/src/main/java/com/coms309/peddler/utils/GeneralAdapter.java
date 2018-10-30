package com.coms309.peddler.utils;

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
 * GeneralAdapter provides corresponding adaptation for FirendList, MarketList.
 */
public class GeneralAdapter extends ArrayAdapter<String> {

    //Fields
    private int[] icons;
    private ArrayList<String> names;
    private ArrayList<String> convs;
    private Context currCtxt;


    //ViewContainer to optimize the repeated res look-up.
    static class ViewContainer {

        //Fields
        ImageView ic;
        TextView na;
        TextView nc;

    }


    //Initializing
    public GeneralAdapter(Context context, ArrayList<String> n, ArrayList<String> c, int[] i){

        super(context, R.layout.friends_row);
        icons = i;
        names = n;
        convs = c;
        currCtxt = context;

    }

    @Override
    public int getCount(){

        return names.size();

    }


    @Override
    public View getView(int pos, View targetV, ViewGroup parent){

        ViewContainer vc = new ViewContainer();

        if(targetV == null) {

            LayoutInflater currInfltr = (LayoutInflater) currCtxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            targetV = currInfltr.inflate(R.layout.friends_row, parent, false);

            //res look-up
            vc.ic = (ImageView) targetV.findViewById(R.id.persIcon);
            vc.na = (TextView) targetV.findViewById(R.id.persName);
            vc.nc = (TextView) targetV.findViewById(R.id.persConv);
            targetV.setTag(vc);

        }
        else{

            vc = (ViewContainer) targetV.getTag();

        }
        vc.ic.setImageResource(icons[0]);
        vc.na.setText(names.get(pos));
        vc.nc.setText(convs.get(0));

        return targetV;

    }


}