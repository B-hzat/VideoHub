package com.example.videohub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videohub.model.DBUser;
import com.example.videohub.model.User;

public class LoginActivity extends AppCompatActivity {

    Button btLogin, btRegister;
    CheckBox cbRemember;
    String username;
    Boolean logState=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btRegister=findViewById(R.id.btRegister);
        cbRemember=findViewById(R.id.cbRemember);

        DBUser db=new DBUser();
        db.AddUser(new User(1,"admin","admin@admin.com","12345"));

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox= preferences.getString("remember","");
        if(checkbox.equals("true")){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("data","admin");
            startActivity(intent);
        }else if (checkbox.equals("false")){
            Toast.makeText(this, "Please Sign-in", Toast.LENGTH_SHORT).show();
        }

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    logState=true;
                    Toast.makeText(LoginActivity.this, "Login will be remembered", Toast.LENGTH_SHORT).show();
                }
                else if(!buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Remember cancelled", Toast.LENGTH_SHORT).show();
                }
            }

        });







    }

    public void Login(View v){

        EditText etUserName = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        TextView tvMessage =  (TextView) findViewById(R.id.tvMessageError);

        username=etUserName.getText().toString().trim();
        String password= etPassword.getText().toString();

        if (username.equals("")){
            tvMessage.setText("Enter your User Name.");
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setTextColor(Color.RED);
            etUserName.requestFocus();
            return ;
        }

        if (password.equals("")){
            tvMessage.setText("Enter your Password.");
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setTextColor(Color.RED);
            etPassword.requestFocus();
            return ;
        }

        if(DBUser.Login(username,password)){
            if(logState) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "true");
                editor.apply();

            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("data", username);
            startActivity(intent);
        }
        else{
            tvMessage.setText("Incorrect username or password");
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setTextColor(Color.RED);
        }


    }
}