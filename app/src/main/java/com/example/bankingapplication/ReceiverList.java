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

public class ReceiverList extends AppCompatActivity
{
    ListView listView;
    public static String receiver_na;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciver_list);

        listView = (ListView) findViewById(R.id.receiver_listview);
        ArrayList<String> arrayList =  new ArrayList<>();

        String sender_name = SenderList.get_val();
        DatabaseHelper my_data = new DatabaseHelper(this);
        SQLiteDatabase database = my_data.getReadableDatabase();
        Cursor cursor = database.rawQuery("select name from Dummy_data", new String[] {});
        if(cursor != null) cursor.moveToFirst();
        do
        {
            String names = cursor.getString(0);
            if(sender_name.equals(names)) continue;
            arrayList.add(names);
        } while(cursor.moveToNext());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id)
            {
                //Toast.makeText(ReceiverList.this, "Clicked Name : "+i+" "+ arrayList.get(i), Toast.LENGTH_SHORT).show();
                receiver_na = arrayList.get(i);
                openConfirmActivity();
            }
        });
    }
    public void openConfirmActivity()
    {
        Intent intent = new Intent(this, Confirm_transfer.class);
        startActivity(intent);
    }
    public static String get_val() { return receiver_na; }
}