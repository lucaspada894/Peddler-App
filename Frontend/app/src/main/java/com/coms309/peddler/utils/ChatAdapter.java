package com.coms309.peddler.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coms309.peddler.R;

import java.util.List;


/**
 * ChatAdapter provides adaptation to chatting pages.
 */
public class ChatAdapter extends RecyclerView.Adapter {

    //Fields
    private Context currCtxt;
    private List messList;
    private static final int NOW_SEND = 1;
    private static final int JUST_REICEIVED = 2;


    //Initializing
    public ChatAdapter(Context context, List messageList) {

        currCtxt = context;
        messList = messageList;

    }

    @Override
    public int getItemCount() {

        return messList.size();

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

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
     * @param position
     * type of message (Receiving or Sending).
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

            case NOW_SEND:

                ((SentMessageHolder) holder).bind(message);
                break;

            case JUST_REICEIVED:

                ((ReceivedMessageHolder) holder).bind(message);
                break;

        }

    }










    //ViewHolders for both receiving and sending messages --- STARTS

    //ViewHolder -> Receiving --- STARTS
    private class ReceiveHolder extends RecyclerView.ViewHolder {

        //Fields
        private TextView messRei, timeRei, nameRei;
        private ImageView iconRei;


        //Initializing
        private ReceiveHolder(View itemView) {

            super(itemView);
            messRei = (TextView) itemView.findViewById(R.id.persConv);
            timeRei = (TextView) itemView.findViewById(R.id.messTime);
            nameRei = (TextView) itemView.findViewById(R.id.persName);
            iconRei = (ImageView) itemView.findViewById(R.id.persIcon);

        }

        //to do...

    }
    //ViewHolder -> Receiving --- ENDS


    //ViewHolder -> Sending --- STARTS
    private class SendHolder extends RecyclerView.ViewHolder {

        //Fields
        private TextView messRei, timeRei;


        //Initializing
        private SendHolder(View itemView) {

            super(itemView);
            messRei = (TextView) itemView.findViewById(R.id.persConv);
            timeRei = (TextView) itemView.findViewById(R.id.messTime);

        }

        //to do...

    }
    //ViewHolder -> Sending --- ENDS

    //ViewHolders for both receiving and sending messages --- ENDS


}