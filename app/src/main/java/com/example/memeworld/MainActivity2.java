package com.example.memeworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    TextView memetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbarMeme);
        setSupportActionBar(toolbar);

//        memetext = findViewById(R.id.memetext);
//        memetext.setText("View Memes");
//        memetext.setVisibility(View.VISIBLE);


    }

    public void viewMemes(View view){
        //Toast.makeText(this, "You clicked the btn", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
        startActivity(intent);
    }
}