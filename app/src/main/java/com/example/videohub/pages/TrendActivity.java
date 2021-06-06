package com.example.videohub.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.videohub.Entities.Post;
import com.example.videohub.Entities.PostTrend;
import com.example.videohub.R;
import com.example.videohub.model.RecyclerAdapterFeed;
import com.example.videohub.model.RecyclerAdapterTrends;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class TrendActivity extends AppCompatActivity {

    ArrayList<Post> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);

        arrayList=new ArrayList<Post>();

        RecyclerView recyclerView=findViewById(R.id.rvTrend);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNvTrend);

        bottomNavigationView.setSelectedItemId(R.id.navigationTrends);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationHome:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationTrends:
                        return true;
                    case R.id.navigationUpload:
                        startActivity(new Intent(getApplicationContext(),PostActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        if(PostTrend.postArrayList!=null)arrayList=PostTrend.postArrayList;
        RecyclerAdapterTrends recyclerAdapterTrends= new RecyclerAdapterTrends(this, arrayList);
        recyclerView.setAdapter(recyclerAdapterTrends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}