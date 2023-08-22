package com.example.restaurantinventorysystem.productList;

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

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.MyViewHolder> {

    Context context;

    ArrayList<Item> list;

    public itemAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itmName,itmType,itmPrice,suppName,quantity;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            imageView=itemView.findViewById(R.id.itemImg);
            itmName=itemView.findViewById(R.id.nameVar);
            itmType=itemView.findViewById(R.id.typeVar);
            itmPrice=itemView.findViewById(R.id.priceVar);
            suppName=itemView.findViewById(R.id.suppVar);
            quantity=itemView.findViewById(R.id.quantityVar);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = list.get(position);

        int itemQuantity=item.getQuantity();


        String quantity= String.valueOf(item.getQuantity());
        String type=String.valueOf(item.getItemType());

        if (type.equalsIgnoreCase("Utilities")) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_shopping_bag_24);
        } else if (type.equalsIgnoreCase("Kitchen")) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_soup_kitchen_24);
        } else if (type.equalsIgnoreCase("Bar")) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_wine_bar_24);
        }

        if (itemQuantity <= 0) {
            holder.imageView.setColorFilter(context.getResources().getColor(R.color.red));
        } else {
            holder.imageView.setColorFilter(context.getResources().getColor(android.R.color.transparent));
        }

        holder.itmName.setText(item.getItemName());
        holder.itmType.setText(type);
        holder.itmPrice.setText(item.getItemPrice());
        holder.suppName.setText(item.getSupplierName());
        holder.quantity.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
