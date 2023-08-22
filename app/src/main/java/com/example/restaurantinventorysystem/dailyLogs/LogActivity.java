package com.example.restaurantinventorysystem.dailyLogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.restaurantinventorysystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<DailyLog> list;
    dailylogAdapter LogAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        String selectedDate = getIntent().getStringExtra("selectedDate");
        textView=findViewById(R.id.txtLog);
        textView.setText("Log for: " +selectedDate);

        recyclerView=findViewById(R.id.logList);
        database= FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Logs").child(selectedDate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        LogAdapter = new dailylogAdapter(this,list);
        recyclerView.setAdapter(LogAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DailyLog dailyLog = dataSnapshot.getValue(DailyLog.class);
                    list.add(dailyLog);
                }

                LogAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

}