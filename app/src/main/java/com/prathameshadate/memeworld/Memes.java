package com.prathameshadate.memeworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Memes extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView meme;
    String currentImageUrl;

    static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ActivityCompat.requestPermissions(Memes.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},7);
        ActivityCompat.requestPermissions(Memes.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},7);
        Toolbar toolbar = findViewById(R.id.toolbarMeme);
        setSupportActionBar(toolbar);
         progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);
        loadMeme();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.download){
            //Toast.makeText(this, "Download menu clicked", Toast.LENGTH_SHORT).show();
            download();
        }
        else if(id == R.id.share){
            //Toast.makeText(this, "Share menu clicked", Toast.LENGTH_SHORT).show();
            share();
        }
        else if(id == R.id.refresh){
            refresh();
        }

        return super.onOptionsItemSelected(item);
    }



    public void nextMeme(View view) {
        loadMeme();
    }



    private void refresh() {
        loadMeme();

    }

    public void download(){
        FileOutputStream fileOutputStream = null;
        File file = getdisc();
        if(!file.exists() && !file.mkdirs())
        {
            Toast.makeText(this, "Couldn't make directory try again", Toast.LENGTH_LONG).show();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "img"+date+".jpeg";
        String file_name = file.getAbsolutePath()+"/"+name;
        File new_file = new File(file_name);
        try{
            fileOutputStream = new FileOutputStream(file_name);
            Bitmap bitmap = viewToBitmap(meme,meme.getWidth(),meme.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            Toast.makeText(this, "Image Saved Successfully", Toast.LENGTH_SHORT).show();
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        refreshGallary(file);

    }
    private File getdisc() {
        File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file,"Meme World");
    }

    private void refreshGallary(File file) {
        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        i.setData(Uri.fromFile(file));
        sendBroadcast(i);
    }

    private static Bitmap viewToBitmap(View view, int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    public void share(){

        if(checkPermission()){

            //Toast.makeText(MainActivity3.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(Memes.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            ActivityCompat.requestPermissions(Memes.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            BitmapDrawable bitmapDrawable = (BitmapDrawable)meme.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            String bitmappath = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Title",null);

            Uri uri = Uri.parse(bitmappath);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpg");
            intent.putExtra(Intent.EXTRA_STREAM,uri);
            startActivity(Intent.createChooser(intent,"Share Via"));
        }
        else {

            requestPermission();
        }

    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(Memes.this, new String[]
                {

                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE

                }, RequestPermissionCode);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean ReadExternalStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteExternalStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    if (ReadExternalStoragePermission && WriteExternalStoragePermission ) {

                        Toast.makeText(Memes.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Memes.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
           
        }
    }

    // Checking the permissions are granted or not
    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;

    }




    public void loadMeme(){


        progressBar.setVisibility(View.VISIBLE);

        String url ="https://meme-api.herokuapp.com/gimme";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            currentImageUrl = response.getString("url");

                            meme = (ImageView)findViewById(R.id.memeimage);


                            Glide.with(Memes.this).load(currentImageUrl).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Memes.this, "Something Went Wrong try to refresh!!", Toast.LENGTH_SHORT).show();

                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    return false;
                                }
                            }).into(meme);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error","error");
                        progressBar.setVisibility(View.INVISIBLE);
                        meme = findViewById(R.id.memeimage);
                        Toast.makeText(Memes.this, "Please Check Your Connection and Refresh!!", Toast.LENGTH_LONG).show();
                        Glide.with(Memes.this).load(R.drawable.errorimage).into(meme);
                    }
                });

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

}