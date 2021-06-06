package com.example.videohub.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videohub.Entities.Comment;
import com.example.videohub.Entities.User;
import com.example.videohub.R;
import com.example.videohub.pages.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<Comment> commentList;

    private FirebaseUser firebaseUser;


    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        return new CommentAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Comment comment= commentList.get(position);

        holder.tvCommentComment.setText(comment.getComment());
        getUserInfo(holder.ivProfileComment,holder.tvUsernameComment,comment.getPublisher());

        holder.tvCommentComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("publisherID", comment.getPublisher());
                context.startActivity(intent);
            }
        });

        holder.ivProfileComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("publisherID", comment.getPublisher());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivProfileComment;
        public TextView tvUsernameComment, tvCommentComment;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileComment=itemView.findViewById(R.id.civProfileComment);
            tvUsernameComment=itemView.findViewById(R.id.tvUsernameComment);
            tvCommentComment=itemView.findViewById(R.id.tvCommentComment);

        }
    }
    private void getUserInfo(ImageView imageView, TextView username, String publisherID){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(publisherID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);
                Glide.with(context).load(user.getImageUrl()).into(imageView);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
