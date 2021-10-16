package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

public class Confirm_transfer extends AppCompatActivity
{
    int bal_curr_1,bal_curr_2;
    String sender_name,receiver_name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_transfer);

        sender_name = SenderList.get_val();
        receiver_name = ReceiverList.get_val();

        TextView sender = findViewById(R.id.sender_disp);
        sender.setText(sender_name);
        TextView receiver = findViewById(R.id.receiver_disp);
        receiver.setText(receiver_name);

        String bal_prev_1;
        DatabaseHelper my_data_1 = new DatabaseHelper(this);
        SQLiteDatabase database_1 = my_data_1.getReadableDatabase();
        Cursor cursor_1 = database_1.rawQuery("select balance from Dummy_data where name = '" + sender_name + "'", new String[] {});
        if(cursor_1 != null) cursor_1.moveToFirst();
        bal_prev_1 = cursor_1.getString(0);
        TextView balance = findViewById(R.id.sender_bal);
        balance.setText(bal_prev_1);
        bal_curr_1 = Integer.parseInt(bal_prev_1);

        String bal_prev_2;
        DatabaseHelper my_data_2 = new DatabaseHelper(this);
        SQLiteDatabase database_2 = my_data_2.getReadableDatabase();
        Cursor cursor_2 = database_2.rawQuery("select balance from Dummy_data where name = '" + receiver_name + "'", new String[] {});
        if(cursor_2 != null) cursor_2.moveToFirst();
        bal_prev_2 = cursor_2.getString(0);
        bal_curr_2 = Integer.parseInt(bal_prev_2);

        Button btn_send = (Button) findViewById(R.id.send_button);
        btn_send.setOnClickListener((View V) -> complete_transaction());
    }
    public void complete_transaction()
    {
        String amt_st,acc_no;
        int amt,final_bal_1,final_bal_2,ac_no;
        DatabaseHelper my_data = new DatabaseHelper(this);
        EditText ed_text = findViewById(R.id.get_amount);
        amt_st = ed_text.getText().toString();
        amt = Integer.parseInt(amt_st);
        if(amt > bal_curr_1)
        {
            Toast.makeText(Confirm_transfer.this,"Transaction failed : Low Balance", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SenderList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
        }
        final_bal_1 = bal_curr_1 - amt;
        final_bal_2 = bal_curr_2 + amt;
        Boolean boo_1 = my_data.update_main(final_bal_1, sender_name);
        my_data.update_main(final_bal_2, receiver_name);
        if(boo_1) Toast.makeText(Confirm_transfer.this,"Transaction Successful", Toast.LENGTH_LONG).show();
        else Toast.makeText(Confirm_transfer.this,"Transaction failed", Toast.LENGTH_LONG).show();

        SQLiteDatabase database = my_data.getReadableDatabase();
        Cursor cursor = database.rawQuery("select acno from Dummy_data where name = '" + sender_name + "'", new String[] {});
        if(cursor != null) cursor.moveToFirst();
        acc_no = cursor.getString(0);
        ac_no = Integer.parseInt(acc_no);

        Date currentTime = Calendar.getInstance().getTime();
        my_data.insert_history(ac_no, sender_name, receiver_name, amt, currentTime.toString());

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}