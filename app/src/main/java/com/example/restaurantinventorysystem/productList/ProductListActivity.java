package com.example.restaurantinventorysystem.productList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.restaurantinventorysystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    itemAdapter ItemAdapter;
    ArrayList<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView=findViewById(R.id.itemList);
        database= FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Items");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        ItemAdapter = new itemAdapter(this,list);
        recyclerView.setAdapter(ItemAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    list.add(item);
                }

                ItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goToAddItems(View view){
        Intent intent= new Intent(this, AddItemsActivity.class);
        startActivity(intent);
    }
}