package com.example.abl.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abl.myapplication.Activities.AddItemActivity;
import com.example.abl.myapplication.Holder.BaseHolder;
import com.example.abl.myapplication.Model.ItemObject;
import com.example.abl.myapplication.R;
import com.example.abl.myapplication.Sqlite.SqliteN;

import java.util.List;

/**
 * Created by ABL on 30.01.2018.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseHolder> {

    Context context;
    List<ItemObject> object_list;
    BaseAdapterClickListener clickListener;

    public interface BaseAdapterClickListener {
        void testClick(String waktu);
    }

    public BaseAdapter(Context context, List<ItemObject> object_list) {
        this.context = context;
        this.object_list = object_list;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, null);
        BaseHolder holder = new BaseHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        holder.time.setText(object_list.get(position).time);
        holder.title.setText(object_list.get(position).title);

        holder.card_item_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddItemActivity.class);
                intent.putExtra("title", object_list.get(position).title);
                intent.putExtra("content", object_list.get(position).content);
                view.getContext().startActivity(intent);
            }
        });


        holder.card_item_note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                SqliteN db_note = new SqliteN(context);
                                db_note.deleteItemSelected(object_list.get(position).time);
                                db_note.getData();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.note_sure_q).setPositiveButton("Да", dialogClickListener)
                        .setNegativeButton("Нет", dialogClickListener).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return object_list.size();
    }
}
