package com.example.snapseedui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{

    Button camera;
    Button gallery;

    private static final int CAM_REQUEST = 1;
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    camera = findViewById(R.id.camera);
    gallery = findViewById(R.id.gallery);
    img=findViewById(R.id.display);

    camera.setOnClickListener(new View.OnClickListener()
    {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {

            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAM_REQUEST);
            }
            else
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAM_REQUEST);
            }

        }
    });

    gallery.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
        //check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission not granted, request it
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else {
                    //permission already granted
                    pickImageFromGallery();

                }

            }else {
                   //system OS is less than marsmallow
                    pickImageFromGallery();
            }
        }
    });
    }

    private void pickImageFromGallery()
    {
        //intent tonext activity

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
        img.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAM_REQUEST)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAM_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

        switch(requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                    getPackageManager().PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else{
                    //permission was denied
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAM_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Intent in = new Intent(getApplicationContext(),Main2Activity.class);
            Bundle b=new Bundle();
            b.putString("PHOTO", String.valueOf(Uri.parse(String.valueOf(photo))));
            startActivity(in);

        }
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();

                    img.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    img.setImageURI(selectedImage);
                }
                break;
        }
    }

}


