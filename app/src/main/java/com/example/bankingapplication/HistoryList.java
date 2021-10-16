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
import java.util.ArrayList;

public class HistoryList extends AppCompatActivity
{
    ListView list_View;
    public static String history;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list_View = (ListView) findViewById(R.id.history_list);
        ArrayList<String> array_List =  new ArrayList<>();

        DatabaseHelper my_data = new DatabaseHelper(this);
        SQLiteDatabase database = my_data.getReadableDatabase();
        Cursor cursor = database.rawQuery("select transact_id from History_data", new String[] {});
        cursor.moveToFirst();
        do
        {
            String t_id = cursor.getString(0);
            array_List.add(t_id);
        } while(cursor.moveToNext());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_List);
        list_View.setAdapter(arrayAdapter);
        list_View.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id)
            {
                history = array_List.get(i);
                openHistoryDetailsActivity(history);
            }
        });
    }
    public void openHistoryDetailsActivity(String his_data)
    {
        Intent intent = new Intent(this, HistoryDetails.class);
        intent.putExtra("data",his_data);
        startActivity(intent);
    }
}