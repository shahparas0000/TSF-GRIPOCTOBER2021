package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HistoryDetails extends AppCompatActivity
{
    Button btn_okay;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        Bundle bundle = getIntent().getExtras();
        String history_data = bundle.getString("data");
        TextView name = findViewById(R.id.sender_name_disp);
        name.setText(history_data);
        int id_t = Integer.parseInt(history_data);

        DatabaseHelper my_data = new DatabaseHelper(this);
        SQLiteDatabase database = my_data.getReadableDatabase();
        Cursor cursor = database.rawQuery("select from_acc_no,from_user,to_user,amount,time from History_data where transact_id = " + id_t, new String[] {});
        if(cursor != null) cursor.moveToFirst();
        String from_ac_no_db = cursor.getString(0);
        String from_user_db = cursor.getString(1);
        String to_user_db = cursor.getString(2);
        String amount_db = cursor.getString(3);
        String time_db = cursor.getString(4);

        TextView from_ac_no = findViewById(R.id.sender_acc_disp);
        TextView from_user = findViewById(R.id.sender_name_disp);
        TextView to_user = findViewById(R.id.receiver_display);
        TextView amount = findViewById(R.id.amount_disp);
        TextView time = findViewById(R.id.time_disp);
        from_ac_no.setText(from_ac_no_db);
        from_user.setText(from_user_db);
        to_user.setText(to_user_db);
        amount.setText(amount_db);
        time.setText(time_db);

        btn_okay = findViewById(R.id.okay_button);
        btn_okay.setOnClickListener((View V) -> openMainActivity());
    }
    public void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}