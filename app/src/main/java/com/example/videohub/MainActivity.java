package com.example.videohub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.videohub.model.Post;
import com.example.videohub.model.RecyclerAdapterFeed;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Post> arrayList;
    public static String PACKAGE_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList=new ArrayList<>();
        PACKAGE_NAME=getPackageName();

        recyclerView=findViewById(R.id.recyclerViewFeed);

        arrayList.add(new Post(arrayList.size()+1,R.drawable.profile1,R.raw.post1,"WOW!","What a view!"));
        arrayList.add(new Post(arrayList.size()+1,R.drawable.profile2,R.raw.post2,"WOW!","What a view!"));
        arrayList.add(new Post(arrayList.size()+1,R.drawable.profile3,R.raw.post3,"WOW!","What a view!"));
        arrayList.add(new Post(arrayList.size()+1,R.drawable.profile2,R.raw.post1,"WOW!","What a view!"));
        arrayList.add(new Post(arrayList.size()+1,R.drawable.profile1,R.raw.post3,"WOW!","What a view!"));
        arrayList.add(new Post(arrayList.size()+1,R.drawable.profile3,R.raw.post2,"WOW!","What a view!"));

        RecyclerAdapterFeed recyclerAdapterFeed= new RecyclerAdapterFeed(this,arrayList);

        recyclerView.setAdapter(recyclerAdapterFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}