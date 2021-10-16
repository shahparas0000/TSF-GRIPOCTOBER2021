package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SenderDetails extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_details);

        TextView name = findViewById(R.id.name_disp);
        name.setText(SenderList.get_val());

        DatabaseHelper my_data = new DatabaseHelper(this);
        SQLiteDatabase database = my_data.getReadableDatabase();
        Cursor cursor = database.rawQuery("select email,mob_no,acno,balance from Dummy_data where name = '" + SenderList.get_val() + "'", new String[] {});
        if(cursor != null) cursor.moveToFirst();
        String email_db = cursor.getString(0);
        String mob_no_db = cursor.getString(1);
        String ac_no_db = cursor.getString(2);
        String balance_db = cursor.getString(3);

        TextView email = findViewById(R.id.email_disp);
        TextView mob_no = findViewById(R.id.mob_disp);
        TextView ac_no = findViewById(R.id.acc_disp);
        TextView bal = findViewById(R.id.bal_disp);
        email.setText(email_db);
        mob_no.setText(mob_no_db);
        ac_no.setText(ac_no_db);
        bal.setText(balance_db);

        Button btn_continue = (Button) findViewById(R.id.continue_btn);
        btn_continue.setOnClickListener((View V) -> openReceiverList());
    }
    public void openReceiverList()
    {
        Intent intent = new Intent(this, ReceiverList.class);
        startActivity(intent);
    }
}