package com.prathameshadate.memeworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView mainlogo, subtext;
    Animation topanim, bottomanim;

    private static int SPLASH_SCREEN = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logo = findViewById(R.id.logoanimation);
        Glide.with(this).load(R.drawable.logoanim).into(logo);

        mainlogo = findViewById(R.id.logo);
        subtext = findViewById(R.id.subtext);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        logo.setAnimation(topanim);
        mainlogo.setAnimation(bottomanim);
        subtext.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Home.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }
}