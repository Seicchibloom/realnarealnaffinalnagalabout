package com.example.signuploginrealtime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        ImageView nav_profile = findViewById(R.id.profile_app);
        nav_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button bookbutton = findViewById(R.id.bookbutton);
        bookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, BookingActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView4 = findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, PalawanPage.class);
                startActivity(intent);
            }
        });

        // Add the OnClickListener for imageView (Cebu)
        ImageView imageViewCebu = findViewById(R.id.imageView);
        imageViewCebu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, CebuPage.class);
                startActivity(intent);
            }
        });
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, BoholPage.class);
                startActivity(intent);
            }
        });
        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, BagiuoPage.class);
                startActivity(intent);
            }
        });
        ImageView imageView5 = findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, BatangasPage.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Hotel1stPage.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton6 = findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Hotel2ndPage.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton7 = findViewById(R.id.imageButton7);
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Hotel3rdPage.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Hotel4thPage.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Hotel5thPage.class);
                startActivity(intent);
            }
        });
    }
}
