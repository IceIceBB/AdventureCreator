package com.example.lmont.adventurecreator;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by lmont on 10/20/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {


    //Set waktu lama SplashScreenActivity
    private static int splashInterval = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.custom_transition);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementEnterTransition(transition);
        getWindow().setSharedElementReturnTransition(transition);

        //getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Fade());

        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                Pair<View, String> pair1 = Pair.create(findViewById(R.id.splash_gamelogo_imageview), "gamelogotransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pair1);
                startActivity(intent, options.toBundle());
                startActivity(intent);
                this.finish();
            }
            private void finish() {
            }
        }, splashInterval);

    };

}