package com.example.restaurantinventorysystem.stockCount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantinventorysystem.R;
import com.example.restaurantinventorysystem.dailyLogs.DailyLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class StockCountActivity extends AppCompatActivity {

    Spinner spinnerProduct;
    DatePickerDialog datePicker;
    EditText date, quantity;
    Button btnRestock;
    TextView currentDateTxt;
    DatabaseReference database;
    DatabaseReference restock;
    DatabaseReference itemReference;
    DatabaseReference priceReference;
    DatabaseReference dateReference;
    Task<Void> logReference;
    ArrayList<String> productList;
    ArrayAdapter<String> adapter;
    String itemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_count);

        database = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Items");
        spinnerProduct = findViewById(R.id.spinnerProduct);
        quantity = findViewById(R.id.editTxtQuantity);
        date = findViewById(R.id.editTxtExpiryDate);
        date.setOnClickListener(v -> fnInvokeDatePicker());

        productList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(StockCountActivity.this, android.R.layout.simple_dropdown_item_1line, productList);
        adapter.notifyDataSetChanged();
        spinnerProduct.setAdapter(adapter);
        ShowData();

        btnRestock = findViewById(R.id.btnRestock);
        btnRestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item, Date, Quantity;

                //Getting values from edit text to be updated
                item = spinnerProduct.getSelectedItem().toString();
                Date = date.getText().toString();
                Quantity = quantity.getText().toString();
                if(TextUtils.isEmpty(Quantity)){
                    Toast.makeText(StockCountActivity.this, "Please add a quantity!", Toast.LENGTH_SHORT).show();
                }else {
                    fnRestock(item, Date, Quantity);
                }
            }
        });
    }

    private void fnInvokeDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(StockCountActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth + "/" + (month + 1) + "/" + (year));
            }
        }, year, month, day);
        datePicker.show();
    }

    private void ShowData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    productList.add(item.child("itemName").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fnRestock(String item, String date, String quantity) {
        DatabaseReference restockRef = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Items");

        int addedQuantity = Integer.valueOf(quantity);

        DatabaseReference itemRef = restockRef.child(item);
        itemRef.child("quantity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int currentQuantity = dataSnapshot.getValue(Integer.class);
                    int totalQuantity = currentQuantity + addedQuantity;

                    itemRef.child("quantity").setValue(totalQuantity)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Quantity updated successfully
                                    Toast.makeText(StockCountActivity.this, "Successfully Restocked!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to update the quantity
                                    // Handle the error case
                                }
                            });
                } else {
                    // Handle the case when the data doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error case
            }
        });

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH)+1;
        int year = cldr.get(Calendar.YEAR);
        String currentDate;

        currentDate=day+"-"+month+"-"+year;

        updateLogs(item, currentDate, addedQuantity);
    }


    private void updateLogs(String itemName, String date, int quantity) {
        String updateType = "Item Restocked";

        DatabaseReference logsRef = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Logs")
                .child(date);

        DatabaseReference priceRef = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Items")
                .child(itemName)
                .child("itemPrice");

        priceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String itemPrice = dataSnapshot.getValue(String.class);
                    double price = Double.parseDouble(itemPrice);
                    double amountSpent = price * quantity;

                    DailyLog dailyLog = new DailyLog(itemName, updateType, quantity, amountSpent);

                    logsRef.child(itemName + " Restock").setValue(dailyLog)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Log entry updated successfully
                                    // You can perform any additional operations here
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to update the log entry
                                    // Handle the error case
                                }
                            });
                } else {
                    // Handle the case when the item price data doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error case
            }
        });
    }





}
