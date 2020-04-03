package com.example.snapseedui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.effect.EffectFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import ja.burhanrashid52.photoeditor.CustomEffect;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class Brightness extends AppCompatActivity {

    PhotoEditorView mphotoEditorView;
    private int PICK_IMAGE_REQUEST = 1;
    SeekBar brightnessSeekbar;
    ImageButton addSeek,minusSeek;
    Button home3,retake3,save;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness);

        addSeek=findViewById(R.id.add_seekBar);
        minusSeek=findViewById(R.id.minus_seekBar);

        home3 = findViewById(R.id.home3);
        retake3 = findViewById(R.id.retake3);
        save=findViewById(R.id.save3);



        final CustomEffect.Builder customEffectNotBuild = new CustomEffect.Builder(EffectFactory.EFFECT_BRIGHTNESS);
        mphotoEditorView=findViewById(R.id.photoEditorViewBrightness);

        final PhotoEditor mPhotoEditor = new PhotoEditor.Builder(this, mphotoEditorView)
                .build();

        brightnessSeekbar=findViewById(R.id.seekBar_brightness);

        brightnessSeekbar.setMax(20);
        brightnessSeekbar.setProgress(10);


        brightnessSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int changed_progress=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changed_progress=progress;
                customEffectNotBuild.setParameter("brightness", (float)changed_progress/10);
                mPhotoEditor.setFilterEffect(customEffectNotBuild.build());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

        addSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brightnessSeekbar.setProgress(brightnessSeekbar.getProgress()+1);

            }
        });
        minusSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brightnessSeekbar.setProgress(brightnessSeekbar.getProgress()-1);
            }
        });

        home3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(Brightness.this,Main2Activity.class);
                startActivity(intent);

            }
        });

        retake3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Brightness.this,Brightness.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mPhotoEditor.saveAsBitmap(new OnSaveBitmap() {
                    @Override
                    public void onBitmapReady(Bitmap saveBitmap) {
                        Toast.makeText(Brightness.this,"Failed Saving",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Brightness.this,Main2Activity.class));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(Brightness.this,"Failed Saving",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                mphotoEditorView.getSource().setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
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
        File myDir = new File(root + "/Pictosa");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "pictosa_edited"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
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
