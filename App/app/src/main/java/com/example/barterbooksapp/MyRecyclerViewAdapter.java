package com.example.barterbooksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.Inet4Address;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<String> titles;
    private List<Integer> images;
    private List<String> authors;
    private List<String> conditions;
    private List<String> locations;
    private List<String> prices;

    public MyRecyclerViewAdapter(Context context, List<String> titles, List<Integer> images, List<String> authors,
                                 List<String> conditions, List<String> locations, List<String> prices){
        this.context = context;
        this.titles = titles;
        this.images = images;
        this.authors = authors;
        this.conditions = conditions;
        this.locations = locations;
        this.prices = prices;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(titles.get(position));
        holder.imageView.setImageResource(images.get(position));
        holder.authorTextView.setText(authors.get(position));
        holder.conditionTextView.setText(conditions.get(position));
        holder.locationTextView.setText(locations.get(position));
        holder.priceTextView.setText(prices.get(position));
        holder.barterCheckBox.setChecked(Math.random() < 0.5);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        TextView authorTextView;
        TextView locationTextView;
        TextView conditionTextView;
        TextView priceTextView;
        CheckBox barterCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.titleText);
            authorTextView = itemView.findViewById(R.id.authorText);
            locationTextView = itemView.findViewById(R.id.locationText);
            conditionTextView = itemView.findViewById(R.id.conditionText);
            priceTextView = itemView.findViewById(R.id.priceText);
            barterCheckBox = itemView.findViewById(R.id.barterCheckBox);

//            On click listener to open a Seller list view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Item Clicked", Toast.LENGTH_SHORT ).show();
                }
            });
        }
    }
}
