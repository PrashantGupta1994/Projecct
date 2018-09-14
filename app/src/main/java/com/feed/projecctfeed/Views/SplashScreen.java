package com.feed.projecctfeed.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.feed.projecctfeed.BaseController.BaseActivity;
import com.feed.projecctfeed.R;

public class SplashScreen extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 4 * 1000;
    private final Handler rHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Delaying the intro screen for 4 sec, post that going to BeerDisplayScreen
                startActivity(new Intent(SplashScreen.this, FeedScreen.class));
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onPause() {
        super.onPause();
        rHandler.removeCallbacksAndMessages(null);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rHandler.removeCallbacksAndMessages(null);
    }
}
