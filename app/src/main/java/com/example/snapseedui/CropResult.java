package com.example.snapseedui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

public class CropResult extends AppCompatActivity {

    Button retake,home,save;
    ImageView displ;
    Uri image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_crop__image);
        displ=findViewById(R.id.crop_content_image);
        retake=findViewById(R.id.retake);
        home=findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropResult.this,Main2Activity.class);
                startActivity(intent);
            }
        });


        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CropResult.this,CropResult.class));
            }
        });


        CropImage.activity().start(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==Activity.RESULT_OK){
                image=result.getUri();
                displ.setImageURI(image);

            }
            else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
               Toast.makeText(this,result.getError().toString(),Toast.LENGTH_LONG).show();
            }
        }
    }
}
