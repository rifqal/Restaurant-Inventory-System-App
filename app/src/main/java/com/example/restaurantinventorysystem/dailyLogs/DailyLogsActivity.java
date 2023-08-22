package com.example.restaurantinventorysystem.dailyLogs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.example.restaurantinventorysystem.R;
import java.util.Calendar;

public class DailyLogsActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    EditText date;
    Button logButton;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_logs);


        date=findViewById(R.id.editTextDate);
        date.setOnClickListener(v-> fnInvokeDatePicker());


        logButton=findViewById(R.id.btnLog);
        logButton.setOnClickListener(v-> fnGenerateLog());


    }

    private void fnInvokeDatePicker()
    {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(DailyLogsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth + "-" + (month + 1) + "-" + (year));
            }
        },year, month, day);
        datePicker.show();
    }

    private void fnGenerateLog(){

        if(TextUtils.isEmpty(date.getText())){
            Toast.makeText(DailyLogsActivity.this,"Please select a date!",Toast.LENGTH_SHORT).show();
        }
        else {
            selectedDate = date.getText().toString();
            Intent intent = new Intent(DailyLogsActivity.this, LogActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        }
    }



}
