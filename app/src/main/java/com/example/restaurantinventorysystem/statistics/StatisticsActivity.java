package com.example.restaurantinventorysystem.statistics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restaurantinventorysystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    EditText date;
    String selectedDate, selectedDataType;
    DatabaseReference logsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        date = findViewById(R.id.editTxtDate);


        date.setOnClickListener(v -> fnInvokeDatePicker());
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the spinner
                selectedDataType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when no item is selected
            }
        });

        // StatisticsActivity.java

        findViewById(R.id.btnBarChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = date.getText().toString();
                if (TextUtils.isEmpty(selectedDate)) {
                    Toast.makeText(StatisticsActivity.this, "Please select a date!", Toast.LENGTH_SHORT).show();
                }else if(selectedDataType.equals("Money Spent")) {
                    fnGenerateMoneySpent();
                }else if(selectedDataType.equals("Items Used")) {
                    fnGenerateItemsUsed();
                }else if(selectedDataType.equals("Items Restocked")) {
                    fnGenerateItemRestocked();
                }

            }
        });


    }

    private void fnInvokeDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(StatisticsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth + "-" + (month + 1) + "-" + (year));
            }
        }, year, month, day);
        datePicker.show();
    }

    private void fnGenerateItemsUsed(){
            String graphsName,colorHex;
            graphsName="Items Used";
            colorHex="#F38744";
            logsRef = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("Logs")
                    .child(selectedDate);

            logsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String[]> data = new ArrayList<>();

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String updatedItemName = childSnapshot.child("updatedItemName").getValue(String.class);
                        int amountUpdated = childSnapshot.child("amountUpdated").getValue(Integer.class);
                        String updateType = childSnapshot.child("updateType").getValue(String.class);

                        if (updateType.equals("Stock Used")) {
                            String[] entry = {updatedItemName, String.valueOf(amountUpdated)};
                            data.add(entry);
                        }
                    }

                    if (!data.isEmpty()) {
                        Intent intent = new Intent(StatisticsActivity.this, chartActivity.class);
                        intent.putExtra("selectedDate", selectedDate);
                        intent.putExtra("data", data.toArray(new String[data.size()][]));
                        intent.putExtra("graphName",graphsName);
                        intent.putExtra("color",colorHex);
                        startActivity(intent);
                    } else {
                        Toast.makeText(StatisticsActivity.this, "No data available for the selected date.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database errors if needed
                }
            });
    }

    private void fnGenerateItemRestocked(){
        String graphName,colorHex;
        graphName="Items Restocked";
        colorHex="#FFC107";
        logsRef = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Logs")
                .child(selectedDate);

        logsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String[]> data = new ArrayList<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String updatedItemName = childSnapshot.child("updatedItemName").getValue(String.class);
                    int amountUpdated = childSnapshot.child("amountUpdated").getValue(Integer.class);
                    String updateType = childSnapshot.child("updateType").getValue(String.class);

                    if (updateType.equals("Item Restocked")) {
                        String[] entry = {updatedItemName, String.valueOf(amountUpdated)};
                        data.add(entry);
                    }
                }

                if (!data.isEmpty()) {
                    Intent intent = new Intent(StatisticsActivity.this, chartActivity.class);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("data", data.toArray(new String[data.size()][]));
                    intent.putExtra("graphName",graphName);
                    intent.putExtra("color",colorHex);
                    startActivity(intent);
                } else {
                    Toast.makeText(StatisticsActivity.this, "No data available for the selected date.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors if needed
            }
        });
    }

    private void fnGenerateMoneySpent(){
            String graphName,colorHex;
            graphName="Items Used";
            colorHex="#77DD76";
            selectedDate = date.getText().toString();
            if (TextUtils.isEmpty(selectedDate)) {
                Toast.makeText(StatisticsActivity.this, "Please select a date!", Toast.LENGTH_SHORT).show();
            } else {
                logsRef = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .getReference("Logs")
                        .child(selectedDate);

                logsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String[]> data = new ArrayList<>();

                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String updatedItemName = childSnapshot.child("updatedItemName").getValue(String.class);
                            int amountSpent = childSnapshot.child("amountSpent").getValue(Integer.class);
                            String updateType = childSnapshot.child("updateType").getValue(String.class);

                            if (amountSpent != 0 && updateType.equals("Item Restocked")) {
                                String[] entry = {updatedItemName, String.valueOf(amountSpent)};
                                data.add(entry);
                            }
                        }

                        if (!data.isEmpty()) {
                            Intent intent = new Intent(StatisticsActivity.this, chartActivity.class);
                            intent.putExtra("selectedDate", selectedDate);
                            intent.putExtra("data", data.toArray(new String[data.size()][]));
                            intent.putExtra("graphName",graphName);
                            intent.putExtra("color",colorHex);
                            startActivity(intent);
                        } else {
                            Toast.makeText(StatisticsActivity.this, "No data available for the selected date.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database errors if needed
                    }
                });
            }
        }
}

