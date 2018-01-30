package com.example.abl.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.abl.myapplication.Adapter.BaseAdapter;
import com.example.abl.myapplication.R;
import com.example.abl.myapplication.Sqlite.SqliteN;

import org.reactivestreams.Subscriber;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.myFAB)
    FloatingActionButton myFAB;
    @BindView(R.id.lst_note) RecyclerView lst_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        lst_note.setLayoutManager(gridLayoutManager);
        ItemDataBase();
        myFAB.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_refresh:
                ItemDataBase();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myFAB:
                Intent intent = new Intent(this, AddItemActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void ItemDataBase() {
        SqliteN db_note = new SqliteN(this);
        BaseAdapter adapter = new BaseAdapter(this, db_note.getData());
        lst_note.setAdapter(adapter);
    }
}
