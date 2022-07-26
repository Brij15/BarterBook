package com.formulator.barterbooksapp.recyclerViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formulator.barterbooksapp.DetailsActivity;
import com.formulator.barterbooksapp.R;
import com.formulator.barterbooksapp.utlity.BookPostDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private List<BookPostDataModel> postList;


    public MyRecyclerViewAdapter(Context context, List<BookPostDataModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(postList.get(position).getTitle());
        holder.authorTextView.setText(postList.get(position).getAuthor());
        holder.conditionTextView.setText(postList.get(position).getCondition());
        holder.locationTextView.setText(postList.get(position).getLocation());
        holder.priceTextView.setText("$"+ postList.get(position).getPrice().toString());
        holder.barterCheckBox.setChecked(Math.random() < 0.5);
        holder.postID.setText(postList.get(position).getPostID());
        if (postList.get(position).getImagesList() == null){
        //            Do default image Here
            holder.imageView.setImageResource(R.drawable.default_book);
        }
        else {
            Uri myUri = Uri.parse(postList.get(position).getImagesList().get(0));
//            Log.d("PIC", postList.get(position).getImagesList().get(0));
            holder.barterCheckBox.setChecked(postList.get(position).getBarter());
            Picasso.get().load(myUri).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        TextView authorTextView;
        TextView locationTextView;
        TextView conditionTextView;
        TextView priceTextView;
        CheckBox barterCheckBox;
        TextView imageURI;
        TextView postID;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.titleText);
            authorTextView = itemView.findViewById(R.id.authorText);
            locationTextView = itemView.findViewById(R.id.locationText);
            conditionTextView = itemView.findViewById(R.id.conditionText);
            priceTextView = itemView.findViewById(R.id.priceText);
            barterCheckBox = itemView.findViewById(R.id.barterCheckBox);
            imageURI = itemView.findViewById(R.id.txtImageURIHidden);
            postID = itemView.findViewById(R.id.txtIDHidden);

//            On click listener to open a Seller list view
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("postID", postID.getText());
                view.getContext().startActivity(intent);
            });
        }
    }

    public void setItems(List<BookPostDataModel> newPostList) {
        this.postList.clear();
        this.postList = newPostList;
    }
}
