package com.example.chamiaapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chamiaapp.R;

public class FirstPage extends AppCompatActivity {
    Animation topAnim , bottomAnim ;
    ImageView image ;
    TextView logo , slogan ;
    private static int SPLASH_SCREEN = 5000 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_page);


        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.imageView2);
        logo = findViewById(R.id.textView2);
        slogan = findViewById(R.id.textView3);

        //Set animation to elements
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstPage.this , Login_page1.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}