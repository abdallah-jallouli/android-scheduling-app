package com.example.chamiaapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamiaapp.MainActivity;
import com.example.chamiaapp.R;
import com.example.chamiaapp.ui.schedule.MainSchedule;
import com.google.android.material.button.MaterialButton;

public class Login_page1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page1);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton continuebtn = (MaterialButton) findViewById(R.id.continuebtn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    //correct
                    Toast.makeText(Login_page1.this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_page1.this , MainActivity.class);
                    startActivity(intent);

                } else if (username.getText().toString().equals("user") && password.getText().toString().equals("user")) {
                    Toast.makeText(Login_page1.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    Intent t = new Intent(Login_page1.this , MainSchedule.class);
                    startActivity(t);

                }else
                //incorrect
                    Toast.makeText(Login_page1.this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}