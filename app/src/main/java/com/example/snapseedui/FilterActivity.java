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
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class FilterActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Uri imUri;
    Button sepia,contrast,negative;
    PhotoEditorView mPhotoEditorView;
    Button retake2,save,home2;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mPhotoEditorView= findViewById(R.id.photoEditorView);
        sepia=findViewById(R.id.sepia);
        contrast=findViewById(R.id.contrast);
        negative=findViewById(R.id.negative);

        retake2=findViewById(R.id.retake2);
        save=findViewById(R.id.save2);
        home2=findViewById(R.id.home2);


        home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });


        retake2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this,FilterActivity.class));
            }
        });

        final PhotoEditor mphoto=new PhotoEditor.Builder(this,mPhotoEditorView).build();



        sepia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mphoto.setFilterEffect(PhotoFilter.SEPIA);

            }
        });
        contrast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mphoto.setFilterEffect(PhotoFilter.CONTRAST);
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mphoto.setFilterEffect(PhotoFilter.NEGATIVE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mphoto.saveAsBitmap(new OnSaveBitmap() {
                    @Override
                    public void onBitmapReady(Bitmap saveBitmap) {
                        Toast.makeText(FilterActivity.this,"Successfully Saved",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FilterActivity.this,Main2Activity.class));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(FilterActivity.this,"Failed Saving",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
          imUri= Uri.parse(String.valueOf(photo));
            mPhotoEditorView.getSource().setImageBitmap(photo);
        }else {
            Toast.makeText(this,"FAILED TO LOAD IMAGE",Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        }else{
            //prompt the user or do something
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Pictosa_"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }



}
