package com.example.restaurantinventorysystem.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantinventorysystem.R;
import com.example.restaurantinventorysystem.productList.Item;

import java.util.ArrayList;

public class supplierAdapter extends RecyclerView.Adapter<supplierAdapter.SupplierViewHolder> {

    Context context;
    ArrayList<Item> list;

    public supplierAdapter(Context context, ArrayList<Item> list){
        this.context=context;
        this.list=list;
    }

    public static class SupplierViewHolder extends RecyclerView.ViewHolder{

        TextView itmName,suppName,suppContact;

        public SupplierViewHolder(@NonNull View itemView){
            super(itemView);

            itmName=itemView.findViewById(R.id.varItm);
            suppName=itemView.findViewById(R.id.varName);
            suppContact=itemView.findViewById(R.id.varContact);
        }
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.supplier,parent,false);
        return new SupplierViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull supplierAdapter.SupplierViewHolder holder, int position) {
        Item item = list.get(position);
        holder.itmName.setText(item.getItemName());
        holder.suppName.setText(item.getSupplierName());
        holder.suppContact.setText(item.getSupplierContact());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
