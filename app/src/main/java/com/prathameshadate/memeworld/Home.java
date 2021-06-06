package com.prathameshadate.memeworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbarMeme);
        setSupportActionBar(toolbar);

    }

    public void viewMemes(View view){
        //Toast.makeText(this, "You clicked the btn", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Home.this, Memes.class);
        startActivity(intent);
    }
}