package com.formulator.barterbooksapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formulator.barterbooksapp.R;
import com.formulator.barterbooksapp.utlity.BookPostDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellerRecycleViewAdapter extends RecyclerView.Adapter<SellerRecycleViewAdapter.SellerViewHolder> {
    private Context context;
    private List<BookPostDataModel> postList;

    public SellerRecycleViewAdapter(Context context, List<BookPostDataModel> postList){
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);
        return new SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull SellerViewHolder holder, int position){
        if(postList.get(position)!= null){
            Log.i("TestBrij", postList.get(position).getTitle());
            holder.textView.setText(postList.get(position).getTitle());
            holder.authorTextView.setText(postList.get(position).getAuthor());
            holder.postID.setText(postList.get(position).getPostID());
            if (postList.get(position).getImagesList() == null){
                //            Do default image Here
                holder.imageView.setImageResource(R.drawable.default_book);
            }
            else {
                Uri myUri = Uri.parse(postList.get(position).getImagesList().get(0));
//            Log.d("PIC", postList.get(position).getImagesList().get(0));
                //holder.barterCheckBox.setChecked(postList.get(position).getBarter());
                Picasso.get().load(myUri).into(holder.imageView);
            }
        }

    }

    @Override
    public int getItemCount(){return postList.size();}

    public static class SellerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView authorTextView;
        TextView postID;

        public SellerViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.lstImageView);
            textView = itemView.findViewById(R.id.lstTitleText);
            authorTextView = itemView.findViewById(R.id.lstAuthorText);
            postID = itemView.findViewById(R.id.txtIDHiddenSellerList);

//            On click listener to open a Seller list view
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("postID", postID.getText());
                view.getContext().startActivity(intent);
            });
        }
    }
}
