package com.example.snapseedui;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenu extends Fragment {


    public MainMenu() {
        // Required empty public constructor
    }
    Button back,crop,filter,brightness,info;
    Uri image;
    ImageView resultImage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_main_menu, container, false);

        filter=v.findViewById(R.id.filter);;
        brightness= v.findViewById(R.id.brightness);
        crop=v.findViewById(R.id.crop);

        info= v.findViewById(R.id.info);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(),information.class);
                startActivity(intent);

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(),FilterActivity.class));
            }
        });


        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(requireActivity(),CropResult.class);
                startActivity(i);
            }
        });

        brightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(),Brightness.class));
            }
        });

        return v;
    }

}
