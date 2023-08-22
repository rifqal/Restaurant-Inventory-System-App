package com.example.restaurantinventorysystem.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantinventorysystem.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.slider.LabelFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class chartActivity extends AppCompatActivity {

    private BarChart barChart;
    private DatabaseReference logsRef;
    String graphName,color;

    // ChartActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        barChart = findViewById(R.id.barChart);
        Intent intent = getIntent();
        String date = intent.getStringExtra("selectedDate");
        graphName = intent.getStringExtra("graphName");
        color = intent.getStringExtra("color");
        String[][] data = (String[][]) intent.getSerializableExtra("data");

        generateBarChart(data);
    }

    private void generateBarChart(String[][] data) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            String updatedItemName = data[i][0];
            int amountSpent = Integer.parseInt(data[i][1]);

            entries.add(new BarEntry(i, amountSpent));
            labels.add(updatedItemName);
        }

        BarDataSet dataSet = new BarDataSet(entries, graphName);
        dataSet.setColor(Color.parseColor(color)); // Customize the bar color if needed
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customize the X-axis labels
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getDescription().setText("Graph of "+graphName);
        barChart.animateY(2000);
        barChart.invalidate(); // Refresh the chart to display the updated data
    }

}
