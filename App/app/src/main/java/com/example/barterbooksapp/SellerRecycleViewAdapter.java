package com.example.barterbooksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SellerRecycleViewAdapter extends RecyclerView.Adapter<SellerRecycleViewAdapter.SellerViewHolder> {
    private Context context;
    private List<String> titles;
    private List<String> authors;
    private List<Integer> images;

    public SellerRecycleViewAdapter(Context context, List<String> titles, List<String> authors, List<Integer> images){
        this.context = context;
        this.titles = titles;
        this.authors = authors;
        this.images = images;
    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);
        return new SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull SellerViewHolder holder, int position){
        holder.imageView.setImageResource(images.get(position));
        holder.textView.setText(titles.get(position));
        holder.authorView.setText(authors.get(position));
    }

    @Override
    public int getItemCount(){return titles.size();}

    public static class SellerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView authorView;

        public SellerViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.lstImageView);
            textView = itemView.findViewById(R.id.lstTitleText);
            authorView = itemView.findViewById(R.id.lstAuthorText);
        }
    }
}
