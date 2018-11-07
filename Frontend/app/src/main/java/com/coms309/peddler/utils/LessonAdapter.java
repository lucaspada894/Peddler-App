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
 * LessonAdapter provides corresponding adaptation for LessonPage.
 */
public class LessonAdapter extends ArrayAdapter<String> {

    //Fields
    private int[] icons;
    private ArrayList<String> names;
    private ArrayList<String> descriptions;
    private Context currCtxt;


    //ViewContainer to optimize the repeated res look-up.
    static class ViewContainer {

        //Fields
        ImageView ic;
        TextView na;
        TextView ds;

    }


    //Initializing
    public LessonAdapter(Context context, ArrayList<String> n, ArrayList<String> c, int[] i){

        super(context, R.layout.lessons_row);
        icons = i;
        names = n;
        descriptions = c;
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
            targetV = currInfltr.inflate(R.layout.lessons_row, parent, false);

            //res look-up
            vc.ic = (ImageView) targetV.findViewById(R.id.lessIcon);
            vc.na = (TextView) targetV.findViewById(R.id.lessName);
            vc.ds = (TextView) targetV.findViewById(R.id.lessDes);
            targetV.setTag(vc);

        }
        else{

            vc = (ViewContainer) targetV.getTag();

        }
        vc.ic.setImageResource(icons[0]);
        vc.na.setText(names.get(pos));
        vc.ds.setText(descriptions.get(pos));

        return targetV;

    }


}