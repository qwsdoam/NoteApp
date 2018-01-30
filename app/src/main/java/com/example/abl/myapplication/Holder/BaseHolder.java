package com.example.abl.myapplication.Holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abl.myapplication.R;

/**
 * Created by ABL on 30.01.2018.
 */

public class BaseHolder extends RecyclerView.ViewHolder {

    public TextView  time;
    public TextView title;
    public CardView card_item_note;

    public BaseHolder(View itemView) {
        super(itemView);
        time = (TextView) itemView.findViewById(R.id.time);
        title = (TextView) itemView.findViewById(R.id.title);
        card_item_note = (CardView) itemView.findViewById(R.id.card_item_note);
    }

}
