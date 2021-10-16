package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class SenderList extends AppCompatActivity
{
    ListView listView;
    public static String sender_na;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_list);

        if(getIntent().getBooleanExtra("EXIT", false))
        {
            Intent intent = new Intent(this, SenderList.class);
            startActivity(intent);
        }

        listView = (ListView) findViewById(R.id.sender_listview);
        ArrayList<String> arrayList =  new ArrayList<>();

        DatabaseHelper my_data = new DatabaseHelper(this);
        SQLiteDatabase database = my_data.getReadableDatabase();
        Cursor cursor = database.rawQuery("select name from Dummy_data", new String[] {});
        if(cursor != null) cursor.moveToFirst();
        do
        {
            String names = cursor.getString(0);
            arrayList.add(names);
        } while(cursor.moveToNext());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id)
            {
                //Toast.makeText(SenderList.this, "Clicked Name : "+i+" "+ arrayList.get(i), Toast.LENGTH_SHORT).show();
                sender_na = arrayList.get(i);
                openSenderDetailsActivity();
            }
        });
    }
    public void openSenderDetailsActivity()
    {
        Intent intent = new Intent(this, SenderDetails.class);
        startActivity(intent);
    }
    public static String get_val() { return sender_na; }
}