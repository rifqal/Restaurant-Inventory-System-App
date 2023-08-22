package com.example.restaurantinventorysystem.stockCount;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantinventorysystem.R;
import com.example.restaurantinventorysystem.dailyLogs.DailyLog;
import com.example.restaurantinventorysystem.productList.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class countAdapter extends RecyclerView.Adapter<countAdapter.CountViewHolder> {

    private Context context;
    private ArrayList<Item> itemList;
    DatabaseReference database;

    public countAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;


        database = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    public static class CountViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView,updateTextView,quantityTextView;
        EditText quantityEditText;

        public CountViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.varCountItem);
            quantityEditText = itemView.findViewById(R.id.edtNewAmount);
            quantityTextView = itemView.findViewById(R.id.edtAmount);
            updateTextView = itemView.findViewById(R.id.txtUpdate);
        }
    }

    @NonNull
    @Override
    public CountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.count, parent, false);
        return new CountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemNameTextView.setText(item.getItemName());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        holder.updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int originalQuantity = item.getQuantity();
                int newQuantity = Integer.parseInt(holder.quantityEditText.getText().toString());
                String quantityInput = holder.quantityEditText.getText().toString().trim();

                if (TextUtils.isEmpty(quantityInput)) {
                    Toast.makeText(context, "Please enter a quantity!", Toast.LENGTH_SHORT).show();
                } else {
                    if (newQuantity < 0) {
                        Toast.makeText(context, "Invalid input, please try again!", Toast.LENGTH_SHORT).show();
                    } else if (newQuantity > originalQuantity) {
                        Toast.makeText(context, "Invalid input, please try again!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Check if the new quantity is different from the original quantity
                        if (newQuantity != originalQuantity) {
                            // Calculate the difference in quantity
                            int quantityDifference = originalQuantity - newQuantity;

                            // Update the quantity value in Firebase
                            DatabaseReference itemRef = database.child("Items").child(item.getItemName()).child("quantity");
                            itemRef.setValue(newQuantity);

                            // Create a new DailyLog object for the update
                            String updateType = "Stock Used";
                            double amountSpent = 0;

                            // Create the Logs reference
                            DatabaseReference logsRef = database.child("Logs").child(getCurrentDate());

                            // Create a new DailyLog object
                            DailyLog dailyLog = new DailyLog(item.getItemName(), updateType, quantityDifference, amountSpent);

                            // Push the new log to the Logs database
                            logsRef.child(item.getItemName() + " Stock Used").setValue(dailyLog);

                            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private String getCurrentDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH)+1;
        int year = cldr.get(Calendar.YEAR);
        String currentDate;

        currentDate=day+"-"+month+"-"+year;
        return currentDate;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
