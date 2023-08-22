package com.example.restaurantinventorysystem.supplier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurantinventorysystem.R;
import com.example.restaurantinventorysystem.productList.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SupplierDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    supplierAdapter SupplierAdapter;
    ArrayList<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_details);

        recyclerView=findViewById(R.id.supplierList);
        database= FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Items");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        SupplierAdapter= new supplierAdapter(this,list);
        recyclerView.setAdapter(SupplierAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    list.add(item);
                }

                SupplierAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}