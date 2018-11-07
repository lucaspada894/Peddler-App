package com.coms309.peddler.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coms309.peddler.Models.Message;
import com.coms309.peddler.Models.User;
import com.coms309.peddler.R;
import com.coms309.peddler.app.AppController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * ChatAdapter provides adaptation for chat/message pages.
 */
public class ChatAdapter extends RecyclerView.Adapter {

    //Fields
    private Context currCtxt;
    private ArrayList<Message> messList;
    private static final int NOW_SEND = 1;
    private static final int JUST_REICEIVED = 2;
    private User curr;


    //Initializing
    public ChatAdapter(Context context, ArrayList<Message> messageList) {

        currCtxt = context;
        messList = messageList;
        curr = AppController.getInstance().CurrentUser;

    }

    @Override
    public int getItemCount() {

        return messList.size();

    }

    @Override
    public int getItemViewType(int pos) {

        Message temp = messList.get(pos);

        if(temp.getSenderID().equals("userID")){

            return NOW_SEND;

        }
        else{

            return JUST_REICEIVED;

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == NOW_SEND) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_mess_row, parent, false);

            return new SendHolder(view);

        }
        else if (viewType == JUST_REICEIVED) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_mess_row, parent, false);

            return new ReceiveHolder(view);

        }
        return null;

    }

    /**
     * MUST-HAVE implementation, passes messages to the contents in corresponding ViewHolder.
     * @param holder
     * target viewHolder
     * @param pos
     * type of message (Receiving or Sending).
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {

        Message temp = messList.get(pos);

        switch (holder.getItemViewType()) {

            case NOW_SEND:

                ((SendHolder) holder).inject(temp);
                break;

            case JUST_REICEIVED:

                ((ReceiveHolder) holder).inject(temp);
                break;

        }

    }

    //ViewHolders for both receiving and sending messages --- STARTS

    //ViewHolder -> Receiving --- STARTS
    private class ReceiveHolder extends RecyclerView.ViewHolder {

        //Fields
        TextView messRei, timeRei, nameRei;
        ImageView iconRei;


        //Initializing
        ReceiveHolder(View itemView) {

            super(itemView);
            messRei = (TextView) itemView.findViewById(R.id.convRei);
            timeRei = (TextView) itemView.findViewById(R.id.timeRei);
            nameRei = (TextView) itemView.findViewById(R.id.nameRei);
            iconRei = (ImageView) itemView.findViewById(R.id.iconRei);

        }

        //Filling messages in components.
        void inject(Message mess){

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            messRei.setText(mess.getMsg());
            timeRei.setText(time);

        }

    }
    //ViewHolder -> Receiving --- ENDS


    //ViewHolder -> Sending --- STARTS
    private class SendHolder extends RecyclerView.ViewHolder {

        //Fields
        TextView messSen, timeSen;


        //Initializing
        SendHolder(View itemView) {

            super(itemView);
            messSen = (TextView) itemView.findViewById(R.id.convSen);
            timeSen = (TextView) itemView.findViewById(R.id.timeSen);

        }

        //Filling messages in components.
        void inject(Message mess){

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            messSen.setText(mess.getMsg());
            timeSen.setText(time);

        }

    }
    //ViewHolder -> Sending --- ENDS

    //ViewHolders for both receiving and sending messages --- ENDS


}