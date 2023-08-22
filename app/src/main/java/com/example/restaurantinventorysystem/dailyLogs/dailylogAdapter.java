package com.example.restaurantinventorysystem.dailyLogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantinventorysystem.R;
import java.util.ArrayList;

public class dailylogAdapter extends RecyclerView.Adapter<dailylogAdapter.logViewHolder> {

    Context context;

    ArrayList<DailyLog> list;

    public dailylogAdapter(Context context, ArrayList<DailyLog> list){
        this.context=context;
        this.list=list;
    }

    public static class logViewHolder extends RecyclerView.ViewHolder {

        TextView itemUpdated, amountUpdated, updateType,amountSpent,txtAmount,txtSpent;
        ImageView imageView;


        public logViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.logImg);
            itemUpdated = itemView.findViewById(R.id.varItem);
            amountUpdated = itemView.findViewById(R.id.varAmount);
            updateType = itemView.findViewById(R.id.varType);
            amountSpent=itemView.findViewById(R.id.varSpent);
            txtAmount=itemView.findViewById(R.id.txtAmount);
            txtSpent=itemView.findViewById(R.id.txtSpent);
        }
    }


    @NonNull
    @Override
    public logViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.dailylog,parent,false);
        return new logViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull logViewHolder holder, int position) {
        DailyLog dailyLog = list.get(position);

        String type=String.valueOf(dailyLog.getUpdateType());

        if(type.equalsIgnoreCase("Item Restocked")){
            holder.imageView.setImageResource(R.drawable.restock);
        }
        else if(type.equalsIgnoreCase("Newly Added Item")){
            holder.imageView.setImageResource(R.drawable.added);
        }
        else if(type.equalsIgnoreCase("Stock Used")){
            holder.imageView.setImageResource(R.drawable.ic_baseline_flatware_24);
        }

        // Set the text values
        holder.itemUpdated.setText(dailyLog.getUpdatedItemName());
        holder.updateType.setText(dailyLog.getUpdateType());

        if (type.equalsIgnoreCase("Newly Added Item")) {
            // Hide the amount related views
            holder.amountUpdated.setVisibility(View.GONE);
            holder.amountSpent.setVisibility(View.GONE);
            holder.txtAmount.setVisibility(View.GONE);
            holder.txtSpent.setVisibility(View.GONE);
        } else if(type.equalsIgnoreCase("Stock Used")) {
            holder.amountSpent.setVisibility(View.GONE);
            holder.txtSpent.setVisibility(View.GONE);
            holder.txtAmount.setText("Amount Used: ");
            String updated = String.valueOf(dailyLog.getAmountUpdated());
            holder.amountUpdated.setText(updated);
        }else {
            // Show the amount related views
            holder.amountUpdated.setVisibility(View.VISIBLE);
            holder.amountSpent.setVisibility(View.VISIBLE);
            holder.txtAmount.setVisibility(View.VISIBLE);
            holder.txtSpent.setVisibility(View.VISIBLE);

            String spent = String.valueOf(dailyLog.getAmountSpent());
            String updated = String.valueOf(dailyLog.getAmountUpdated());

            holder.amountUpdated.setText(updated);
            holder.amountSpent.setText(spent);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
