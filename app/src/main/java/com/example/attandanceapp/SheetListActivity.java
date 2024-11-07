package com.example.attandanceapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {
    private ListView sheetList;
    private ArrayAdapter adapter;
    private ArrayList listItems = new ArrayList();
    private long cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sheet_list);

        cid= getIntent().getLongExtra("cid",-1);

        loadListItems();
        sheetList = findViewById(R.id.sheetList);
        adapter =  new ArrayAdapter(this,R.layout.sheet_list, R.id.date_list_item, listItems);

        sheetList.setAdapter(adapter);

    }



    private void loadListItems() {
        Cursor cursor = new DbHelper(this).getDistinctMonths(cid);
            while (cursor.moveToNext()) {

                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DbHelper.DATE_KEY));

                    listItems.add(date.substring(3));

            }


    }

}