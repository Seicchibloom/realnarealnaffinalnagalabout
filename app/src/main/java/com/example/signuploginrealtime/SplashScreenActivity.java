package com.example.signuploginrealtime;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 5000; // 5 seconds
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Initialize MediaPlayer with the splash sound
        mediaPlayer = MediaPlayer.create(this, R.raw.splash_sound);

        ImageView logoImageView = findViewById(R.id.logoImageView);

        // Create fade-out animation
        AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeOutAnimation.setDuration(5000); // 5 seconds
        fadeOutAnimation.setFillAfter(true);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start main activity after animation ends
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

        });

        // Apply animation to logo
        logoImageView.startAnimation(fadeOutAnimation);

        // Play the splash sound
        mediaPlayer.start();

        // Delay to simulate splash screen
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Stop the sound when the delay is over
                mediaPlayer.stop();
                // Start main activity after delay
            }
        }, SPLASH_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer when the activity is destroyed
        mediaPlayer.release();
    }
}


