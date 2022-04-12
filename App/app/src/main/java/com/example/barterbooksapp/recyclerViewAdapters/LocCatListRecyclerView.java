package com.example.barterbooksapp.recyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterbooksapp.MainActivity;
import com.example.barterbooksapp.R;

import java.util.List;

public class LocCatListRecyclerView extends RecyclerView.Adapter<LocCatListRecyclerView.MyViewHolder>{
    private Context context;
    private List<String> list;

    public LocCatListRecyclerView(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_location_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listTextView);

//            On click listener to filter
            itemView.setOnClickListener(view -> {//
//                Toast.makeText(view.getContext(), "Item Clicked" + textView.getText(), Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("FilterID", textView.getText());

                String pageContext = view.getContext().getClass().toString();
                if (pageContext.equals("class com.example.barterbooksapp.LocationActivity") ){
                    Log.i("Context", "Im Inside Location");
                    intent.putExtra("PageID", "LOCATION");
                }
                else if(pageContext.equals("class com.example.barterbooksapp.CategoriesActivity") ){
                    Log.i("Context", "Im Inside Category");
                    intent.putExtra("PageID", "CATEGORY");
                }
                view.getContext().startActivity(intent);
            });
        }
    }
}
