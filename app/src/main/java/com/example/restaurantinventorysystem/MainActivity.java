package com.example.restaurantinventorysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantinventorysystem.dailyLogs.DailyLogsActivity;
import com.example.restaurantinventorysystem.login.LoginActivity;
import com.example.restaurantinventorysystem.productList.ProductListActivity;
import com.example.restaurantinventorysystem.statistics.StatisticsActivity;
import com.example.restaurantinventorysystem.stockCount.CountActivity;
import com.example.restaurantinventorysystem.stockCount.StockCountActivity;
import com.example.restaurantinventorysystem.supplier.SupplierDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //DrawerLayout drawerLayout;

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;
    String establishmentName;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //drawerLayout=findViewById(R.id.my_drawer_layout);

        auth=FirebaseAuth.getInstance();
        button=findViewById(R.id.btnLogout);
        textView=findViewById(R.id.txtHome);
        user=auth.getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            reference = FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Establishment");
            reference.child("Name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot dataSnapshot=task.getResult();
                    establishmentName=String.valueOf(dataSnapshot.getValue());
                    textView.setText(establishmentName);
                }
            });
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goToProductList(View view){
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    public void goToStockCount(View view){
        Intent intent = new Intent(this, StockCountActivity.class);
        startActivity(intent);
    }

    public void goToDailyLogs(View view){
        Intent intent = new Intent(this, DailyLogsActivity.class);
        startActivity(intent);
    }

    public void goToSupplierDetails(View view){
        Intent intent = new Intent(this, SupplierDetailsActivity.class);
        startActivity(intent);
    }

    public void goToStatistics(View view){
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void goToCount(View view){
        Intent intent = new Intent(this, CountActivity.class);
        startActivity(intent);
    }

    /*
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout){

        drawerLayout.openDrawer(GravityCompat.START);

    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);


        }

    }

    public void Clickhome(View view){
        recreate();
    }

    public void clickItemList(View view){
        redirectActivity(this,ProductListActivity.class);
    }

    public void clickStockCount(View view){
        redirectActivity(this,StockCountActivity.class);
    }

    public void clickDailyLogs(View view){
        redirectActivity(this,DailyLogsActivity.class);
    }

    public void clickStatistics(View view){
        redirectActivity(this,StatisticsActivity.class);
    }

    public void clickSupplierDetails(View view){
        redirectActivity(this,SupplierDetailsActivity.class);
    }

    private static void redirectActivity(Activity activity,Class Class){

        Intent intent = new Intent(activity,Class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
    */
}