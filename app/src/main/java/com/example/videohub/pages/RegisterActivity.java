package com.example.videohub.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videohub.R;
import com.example.videohub.model.DBUser;
import com.example.videohub.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    String name,email,password,rePassword;
    TextView tvError;
    EditText etUsername,etEmail,etPassword,etRePassword;
    Button btnRegister;
    ProgressDialog pd;


    FirebaseAuth firebaseAuth;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvError=findViewById(R.id.tvErrorRegister);
        etUsername=findViewById(R.id.etUsernameRegister);
        etEmail=findViewById(R.id.etEmailRegister);
        etPassword=findViewById(R.id.etPasswordRegister);
        etRePassword=findViewById(R.id.etRePasswordRegister);
        btnRegister=findViewById(R.id.btnRegister);

        firebaseAuth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String strUsername=etUsername.getText().toString();
                String strEmail= etEmail.getText().toString();
                String strName=etUsername.getText().toString();
                String strPassword=etPassword.getText().toString();
                controlComponents();
                register(strUsername,strEmail,strPassword,strName);

            }
        });


//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                controlComponents();
//                User user= new User(DBUser.GetAllUsers().size()+1,name,email,password);
//                int isAdd=DBUser.AddUser(user);
//                if(isAdd==1){
//                    tvError.setText("User "+name+" has been added on database");
//                    tvError.setTextColor(Color.GREEN);
//                    tvError.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }
    private void controlComponents(){
        name=etUsername.getText().toString().trim();
        email=etEmail.getText().toString().trim();
        password=etPassword.getText().toString();
        rePassword=etRePassword.getText().toString();

        if(name.equals("")){
            tvError.setText("Please enter a name");
            tvError.setTextColor(Color.RED);
            etUsername.requestFocus();
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        if(email.equals("")){
            tvError.setText("Please enter a email");
            tvError.setTextColor(Color.RED);
            etEmail.requestFocus();
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        if(password.equals("")){
            tvError.setText("Please enter a password");
            tvError.setTextColor(Color.RED);
            etPassword.requestFocus();
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        if(rePassword.equals("")){
            tvError.setText("Please enter a password again");
            tvError.setTextColor(Color.RED);
            etRePassword.requestFocus();
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        if (!password.equals(rePassword)){
            tvError.setText("Passwords does not match");
            tvError.setTextColor(Color.RED);
            etRePassword.requestFocus();
            tvError.setVisibility(View.VISIBLE);
            return;
        }

    }

    private void register(String username, String email, String password, String name){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userID=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id",userID);
                            hashMap.put("username",username.toLowerCase());
                            hashMap.put("name",name);
                            hashMap.put("imageUrl","https://firebasestorage.googleapis.com/v0/b/videohubfirebase.appspot.com/o/312-3120300_default-profile-hd-png-download.png?alt=media&token=d96c2f48-caa2-4994-b961-9fa3c222d77a");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}