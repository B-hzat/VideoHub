package com.example.videohub.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videohub.R;

import java.util.ArrayList;

public class RecyclerAdapterFeed extends RecyclerView.Adapter<RecyclerAdapterFeed.ViewHolder> {

    ArrayList<Post> arrayList;

    public  RecyclerAdapterFeed(ArrayList<Post> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapterFeed.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_feed,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterFeed.ViewHolder holder, int position) {
        Post post = arrayList.get(position);

        holder.title.setText(post.getTitle());
        holder.message.setText(post.getMessage());
        holder.profileImage.setImageResource(post.getProfileIcon());
        holder.postImage.setImageResource(post.getPostImage());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage, postImage;
        TextView title, message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage=itemView.findViewById(R.id.ivProfileFeed);
            postImage=itemView.findViewById(R.id.ivPostFeed);
            title=itemView.findViewById(R.id.tbTitleFeed);
            message=itemView.findViewById(R.id.tbMessageFeed);
        }
    }
}
