package com.example.videohub.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btLogin, btRegister;
    CheckBox cbRemember;
    EditText etEmail,etPassword;
    String username;
    Boolean logState=false;

    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btRegister=findViewById(R.id.btRegister);
        cbRemember=findViewById(R.id.cbRemember);
        btLogin=findViewById(R.id.btLogin);
        etEmail=findViewById(R.id.etMailLogin);
        etPassword=findViewById(R.id.etPasswordLogin);


        firebaseAuth= FirebaseAuth.getInstance();


//
//        DBUser db=new DBUser();
//        db.AddUser(new User(1,"admin","admin@admin.com","12345"));
//
//        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//        String checkbox= preferences.getString("remember","");
//        if(checkbox.equals("true")){
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("data","admin");
//            startActivity(intent);
//        }else if (checkbox.equals("false")){
//            Toast.makeText(this, "Please Sign-in", Toast.LENGTH_SHORT).show();
//        }

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd= new ProgressDialog(LoginActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String strEmail =etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();

                if(TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword)){
                    Toast.makeText(LoginActivity.this, "All Fields are required!", Toast.LENGTH_SHORT).show();
                }else{

                    firebaseAuth.signInWithEmailAndPassword(strEmail,strPassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(firebaseAuth.getCurrentUser().getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                pd.dismiss();
                                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                pd.dismiss();
                                            }
                                        });
                                    } else{
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

//        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(buttonView.isChecked()){
//                    logState=true;
//                    Toast.makeText(LoginActivity.this, "Login will be remembered", Toast.LENGTH_SHORT).show();
//                }
//                else if(!buttonView.isChecked()) {
//                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                    SharedPreferences.Editor editor= preferences.edit();
//                    editor.putString("remember","false");
//                    editor.apply();
//                    Toast.makeText(LoginActivity.this, "Remember cancelled", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });







    }

//    public void Login(View v){
//
//        EditText etUserName = (EditText) findViewById(R.id.etUsername);
//        EditText etPassword = (EditText) findViewById(R.id.etPassword);
//        TextView tvMessage =  (TextView) findViewById(R.id.tvMessageError);
//
//        username=etUserName.getText().toString().trim();
//        String password= etPassword.getText().toString();
//
//        if (username.equals("")){
//            tvMessage.setText("Enter your User Name.");
//            tvMessage.setVisibility(View.VISIBLE);
//            tvMessage.setTextColor(Color.RED);
//            etUserName.requestFocus();
//            return ;
//        }
//
//        if (password.equals("")){
//            tvMessage.setText("Enter your Password.");
//            tvMessage.setVisibility(View.VISIBLE);
//            tvMessage.setTextColor(Color.RED);
//            etPassword.requestFocus();
//            return ;
//        }
//
//        if(DBUser.Login(username,password)){
//            if(logState) {
//                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("remember", "true");
//                editor.apply();
//
//            }
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("data", username);
//            startActivity(intent);
//        }
//        else{
//            tvMessage.setText("Incorrect username or password");
//            tvMessage.setVisibility(View.VISIBLE);
//            tvMessage.setTextColor(Color.RED);
//        }
//
//
//    }
}