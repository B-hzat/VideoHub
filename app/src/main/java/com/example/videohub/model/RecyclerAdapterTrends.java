package com.example.videohub.model;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videohub.Entities.Post;
import com.example.videohub.R;
import com.example.videohub.pages.CommentActivity;
import com.example.videohub.pages.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class RecyclerAdapterTrends extends RecyclerView.Adapter<RecyclerAdapterTrends.ViewHolder> {

    ArrayList<Post> arrayList;
    Context context;


    public RecyclerAdapterTrends(Context context, ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterTrends.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterTrends.ViewHolder holder, int position) {
        Post post = arrayList.get(position);
        String videoPath = "android.resource://" + MainActivity.PACKAGE_NAME + "/" + post.getPostImage();
        Uri uri = Uri.parse(videoPath);
        holder.title.setText(post.getTitle());
        holder.message.setText(post.getMessage());
        holder.profileImage.setImageResource(post.getProfileIcon());
//        holder.postImage.setImageResource(post.getPostImage());
        holder.postImage.setVideoURI(uri);

        MediaController mediaController = new MediaController(context);
        holder.postImage.setMediaController(mediaController);
        mediaController.setAnchorView(holder.postImage);
        getComments(post.getPostId(), holder.tvComments);

       holder.likeButton.setOnLikeListener(new OnLikeListener() {
           @Override
           public void liked(LikeButton likeButton) {
               Toast.makeText(context, "Like count is " + post.getLikeCount(), Toast.LENGTH_SHORT).show();
           }

           @Override
           public void unLiked(LikeButton likeButton) {

           }
       });


        holder.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postID", post.getPostId());
                intent.putExtra("publisherID", post.getPublisher());
                context.startActivity(intent);
            }
        });
        holder.tvComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postID", post.getPostId());
                intent.putExtra("publisherID", post.getPublisher());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView title, message, tvComments;
        VideoView postImage;
        ImageView ivComment;
        LikeButton likeButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.ivProfileFeed);
            postImage = itemView.findViewById(R.id.vidPostFeed);
            title = itemView.findViewById(R.id.tbTitleFeed);
            message = itemView.findViewById(R.id.tbMessageFeed);
            ivComment = itemView.findViewById(R.id.ivCommentFeed);
            tvComments = itemView.findViewById(R.id.tvCommenCount);
            likeButton=itemView.findViewById(R.id.ivThumbUpFeed);

        }
    }

    private void getComments(String postID, TextView comments) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.setText("View All " + snapshot.getChildrenCount() + " Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}