package com.example.reto3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.provider.MediaStore;
import android.net.Uri;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;


public class MainActivity extends AppCompatActivity {
    public ImageView img;
    public ImageButton foto;
    public ImageButton video;
    public ImageButton gallery;
    public TextView tv ;
    public VideoView vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foto = (ImageButton)findViewById(R.id.foto);
        video = (ImageButton)findViewById(R.id.video);
        gallery = (ImageButton)findViewById(R.id.gallery);
        img = (ImageView)findViewById(R.id.imageView);
       // tv = (TextView)findViewById(R.id.textView);
        vid = (VideoView)findViewById(R.id.videoView);

        img.setVisibility(View.INVISIBLE);
        vid.setVisibility(View.INVISIBLE);
     //   tv.setText(" ");
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    static final int SELECT_IMAGE = 3;


    public void galeria(View V){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }
    public void tomarFoto(View v) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        String fecha = dateFormat.format(date);
        String fotoText = "myapp"+ fecha;
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }catch(Exception s){
           // tv.setText(s.toString());
        }
    }

    public void tomarVideo(View v) {
        img.setVisibility(View.INVISIBLE);
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        }catch(Exception s){
           // tv.setText(s.toString());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
                vid.setVisibility(View.INVISIBLE);
                img.setVisibility(View.VISIBLE);
                Uri selectedImage = data.getData();
                img.setImageURI(selectedImage);


            }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            vid.setVisibility(View.INVISIBLE);
            img.setVisibility(View.VISIBLE);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);

        }


    if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
        Uri videoUri = (Uri)data.getData();
        vid.setVisibility(View.VISIBLE);
        img.setVisibility(View.INVISIBLE);
        vid.setVideoURI(videoUri);
        vid.start();
    }
}catch(Exception n){
    tv.setText(n.toString());
}

    }





}
