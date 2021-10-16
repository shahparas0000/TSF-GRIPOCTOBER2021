package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button btn_transfer, btn_history;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase my_data = new DatabaseHelper(this).getWritableDatabase();
        btn_transfer = (Button) findViewById(R.id.transfer_button);
        btn_transfer.setOnClickListener((View V) -> openTransferActivity());
        btn_history = (Button) findViewById(R.id.history_button);
        btn_history.setOnClickListener((View V) -> openHistoryActivity());

        if(getIntent().getBooleanExtra("EXIT", false))
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void openTransferActivity()
    {
        Intent intent = new Intent(this, SenderList.class);
        startActivity(intent);
    }
    public void openHistoryActivity()
    {
        Intent intent = new Intent(this, HistoryList.class);
        startActivity(intent);
    }
}