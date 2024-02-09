package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.RamanoraGlobal.InDeoAppNew.R;


public class SplashActivity extends AppCompatActivity {

    private VideoView videoview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Animation animation = new AlphaAnimation(0, 1); //to change visibility from visible to invisible
        animation.setDuration(6000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.

        videoview=findViewById(R.id.videoview);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoview);
        videoview.setMediaController(mediaController);
       /* videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.splashvideo));
        videoview.start();*/
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, LoginScreen.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}