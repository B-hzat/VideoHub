package com.example.videohub.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.videohub.R;
import com.example.videohub.Entities.Post;
import com.example.videohub.model.RecyclerAdapterFeed;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNvMain);

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationHome:
                        return true;
                    case R.id.navigationTrends:
                        startActivity(new Intent(getApplicationContext(),TrendActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationUpload:
                        startActivity(new Intent(getApplicationContext(),PostActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        recyclerView=findViewById(R.id.recyclerViewFeed);

        arrayList.add(new Post(String.valueOf(arrayList.size()+1),R.drawable.profile1,R.raw.post1,"WOW!","What a video!"));
        arrayList.add(new Post(String.valueOf(arrayList.size()+1),R.drawable.profile2,R.raw.post2,"WOW!","What a video!"));
        arrayList.add(new Post(String.valueOf(arrayList.size()+1),R.drawable.profile3,R.raw.post3,"WOW!","What a video!"));
        arrayList.add(new Post(String.valueOf(arrayList.size()+1),R.drawable.profile2,R.raw.post1,"WOW!","What a video!"));
        arrayList.add(new Post(String.valueOf(arrayList.size()+1),R.drawable.profile1,R.raw.post3,"WOW!","What a video!"));
        arrayList.add(new Post(String.valueOf(arrayList.size()+1),R.drawable.profile3,R.raw.post2,"WOW!","What a video!"));




        RecyclerAdapterFeed recyclerAdapterFeed= new RecyclerAdapterFeed(this,arrayList);

        recyclerView.setAdapter(recyclerAdapterFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle intent = getIntent().getExtras();
        if(intent!=null){
            String publisher= intent.getString("publisherID");

            SharedPreferences.Editor editor=getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileID",publisher);
            editor.apply();

        }

    }
}