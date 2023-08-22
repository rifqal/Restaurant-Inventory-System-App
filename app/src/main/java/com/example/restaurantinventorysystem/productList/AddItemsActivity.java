package com.example.restaurantinventorysystem.productList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantinventorysystem.R;
import com.example.restaurantinventorysystem.dailyLogs.DailyLog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class AddItemsActivity extends AppCompatActivity {

    EditText itemName,itemPrice,supplierName,supplierContact;
    Spinner itemType;

    DatabaseReference databaseItems,databaseLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        databaseItems= FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Items");

        findViewById(R.id.btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnAddItem(v);
            }
        });


    }

    private void fnAddItem(View view)
    {
        itemName=findViewById(R.id.editTxtItemName);
        itemPrice=findViewById(R.id.editTxtPrice);
        supplierName=findViewById(R.id.editTxtSupplierName);
        supplierContact=findViewById(R.id.editTxtSupplierContact);
        itemType=findViewById(R.id.spinnerType);

        String itmName=itemName.getText().toString();
        String itmPrice=itemPrice.getText().toString();
        String suppName=supplierName.getText().toString();
        String suppContact=supplierContact.getText().toString();
        String type=itemType.getSelectedItem().toString();

        if(TextUtils.isEmpty(itmName)||TextUtils.isEmpty(itmPrice)||TextUtils.isEmpty(suppName)||TextUtils.isEmpty(suppContact)||TextUtils.isEmpty(type)){
            Toast.makeText(AddItemsActivity.this, "Field cannot be left empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            //Adding items to Item Directory
            String id = databaseItems.push().getKey();

            Item item1 = new Item(itmName,itmPrice,suppName, suppContact, type);

            databaseItems.child(itmName).setValue(item1);

            Toast.makeText(this,"Item added!",Toast.LENGTH_LONG).show();

            //Adding Log to Logs directory
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH)+1;
            int year = cldr.get(Calendar.YEAR);
            String currentDate;

            currentDate=day+"-"+month+"-"+year;


            databaseLogs=FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Logs").child(currentDate);

            DailyLog dailyLog = new DailyLog(itmName,"Newly Added Item",0 ,0);

            databaseLogs.child(itmName+" Added").setValue(dailyLog);




        }

    }
}