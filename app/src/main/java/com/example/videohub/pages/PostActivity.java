package com.example.videohub.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videohub.Entities.Member;
import com.example.videohub.Entities.User;
import com.example.videohub.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private static final int PICK_VIDEO=1;
    ProgressBar progressBar;
    Uri videoUri;
    MediaController mediaController;
    String myUrl="";
    StorageTask storageTask;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Member member;
    User user;
    UploadTask uploadTask;
    VideoView addedVideo;
    ImageView close;
    TextView post;
    EditText title, description;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        member=new Member();
        user=new User();
        progressBar=findViewById(R.id.progressBUpload);
        storageReference= FirebaseStorage.getInstance().getReference("Video");
        databaseReference= FirebaseDatabase.getInstance().getReference("video");
        close=findViewById(R.id.ivCloseUpload);
        addedVideo =findViewById(R.id.vvAddedVideoUpload);
        post=findViewById(R.id.tvPostUpload);
        title=findViewById(R.id.etTitleUpload);
        description=findViewById(R.id.etDescriptionUpload);
        mediaController=new MediaController(this);
        addedVideo.setMediaController(mediaController);
        addedVideo.start();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNvPost);

        bottomNavigationView.setSelectedItemId(R.id.navigationUpload);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationHome:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationTrends:
                        startActivity(new Intent(getApplicationContext(),TrendActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationUpload:
                        return true;
                }
                return false;
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadVideo();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_VIDEO || resultCode == RESULT_OK || data!=null || data.getData() != null){
            videoUri =data.getData();
            addedVideo.setVideoURI(videoUri);
        }

    }

    public void ChooseVideo(View view){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO);
    }

    //Determinate what is extension, mp4 or jpg?
    private String getExt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void UploadVideo(){
        String videoName=title.getText().toString();
        String desc=description.getText().toString();
        if(videoUri!=null || !TextUtils.isEmpty(videoName)){
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference =storageReference.child(System.currentTimeMillis() + "." + getExt(videoUri));
            uploadTask = reference.putFile(videoUri);

//            Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task task) throws Exception {
//                    if(!task.isSuccessful()){
//                        throw task.getException();
//                    }
//                    return  reference.getDownloadUrl();
//                }
//            })
//                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            Uri downloadUrl=task.getResult();
//                        }
//                    });
            Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()) {
                                Uri downloadUrl = (Uri) task.getResult();
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(PostActivity.this, "Data saved!", Toast.LENGTH_SHORT).show();

                                member.setName(videoName);

                                member.setVideoUrl(downloadUrl.toString());
                                String i= databaseReference.push().getKey();
                                databaseReference.child(i).setValue(member);
                            }else{
                                Toast.makeText(PostActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
        }
    }
}