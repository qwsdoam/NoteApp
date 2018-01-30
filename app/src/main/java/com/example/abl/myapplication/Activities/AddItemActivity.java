package com.example.abl.myapplication.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.abl.myapplication.R;
import com.example.abl.myapplication.Sqlite.SqliteN;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ABL on 30.01.2018.
 */

public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView text_title;
    @BindView(R.id.content) TextView text_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        text_title.setText(getIntent().getStringExtra("title"));
        text_content.setText(getIntent().getStringExtra("content"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!text_content.getText().toString().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
            String time_now = sdf.format(new Date());
            SqliteN db_note = new SqliteN(this);
            db_note.addData(time_now, text_title.getText().toString(), text_content.getText().toString(), "null", "null");
        }
    }
}