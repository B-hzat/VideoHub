package com.example.videohub.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videohub.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    Button btnUpload, btnUploadFile;
    TextView tvFileName;
    String file_path=null;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        btnUpload=findViewById(R.id.btnSelectFile);
        btnUploadFile=findViewById(R.id.btnUploadFile);
        tvFileName=findViewById(R.id.tvFileName);
        progressBar=findViewById(R.id.pbUpload);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        filePicker();
                    }
                    else{
                        requestPermission();
                    }
                }
                else{
                    filePicker();
                }
            }
        });

        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file_path!=null){
                    uploadFile();
                }
                else{
                    Toast.makeText(UploadActivity.this, "Please Select File First", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void uploadFile(){
        UploadTask uploadTask=new UploadTask();
        uploadTask.execute(new String[]{file_path});
    }

    private void filePicker(){
        Toast.makeText(UploadActivity.this, "file picker called", Toast.LENGTH_SHORT).show();
        Intent openGallery= new Intent(Intent.ACTION_PICK);
        openGallery.setType("image/*");
        startActivityForResult(openGallery,REQUEST_GALLERY);
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(UploadActivity.this, "Please give permission to upload", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }
    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(UploadActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(UploadActivity.this, "Permission Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UploadActivity.this, "Permission Failed", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GALLERY && resultCode== Activity.RESULT_OK){
            String filePath= getRealPathFromUri(data.getData(),UploadActivity.this);
            Log.d("File Path : ", " "+filePath);
            this.file_path=filePath;
            File file=new File(filePath);
            tvFileName.setText(file.getName());
        }
    }

    public String getRealPathFromUri(Uri uri, Activity activity){
        Cursor cursor=activity.getContentResolver().query(uri,null,null,null,null);
        if(cursor==null){
            return  uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }

    public class UploadTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(UploadActivity.this, "File uploaded", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(UploadActivity.this, "Failed Upload", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            if(uploadFile(strings[0])){
                return "true";
            }
            else{
                return "failed";
            }
        }

        private boolean uploadFile(String path){
            File file=new File(path);
            try {
                RequestBody requestBody= new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("files",file.getName(),RequestBody.create(MediaType.parse("image/*"),file))
                        .addFormDataPart("some_key","some_value")
                        .addFormDataPart("submit","submit")
                        .build();

                Request request=new Request.Builder()
                        .url("http://192.168.1.29/mobileProject/upload.php")
                        .post(requestBody)
                        .build();


                OkHttpClient client= new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    }
                });
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
}