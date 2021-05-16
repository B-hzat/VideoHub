package com.example.videohub.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.videohub.R;
import com.example.videohub.model.DBUser;
import com.example.videohub.Entities.User;

public class RegisterActivity extends AppCompatActivity {

    String name,email,password,rePassword;
    TextView tvError;
    EditText etUsername,etEmail,etPassword,etRePassword;
    Button btnRegister;

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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlComponents();
                User user= new User(DBUser.GetAllUsers().size()+1,name,email,password);
                int isAdd=DBUser.AddUser(user);
                if(isAdd==1){
                    tvError.setText("User "+name+" has been added on database");
                    tvError.setTextColor(Color.GREEN);
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        });
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

}