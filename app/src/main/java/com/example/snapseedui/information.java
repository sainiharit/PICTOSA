package com.example.snapseedui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class information extends AppCompatActivity {

    Button home3,github,gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        home3=findViewById(R.id.home3);
        github=findViewById(R.id.github);
        gmail=findViewById(R.id.gmail);

        home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(information.this,Main2Activity.class);
                startActivity(intent);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL="https://github.com/sainiharit/PICTOSA--The-Photo-Editing-application";
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL));
                startActivity(i);

            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL="https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL));
                startActivity(i);

            }
        });
    }

}
