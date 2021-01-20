package com.jrmod.app.makhroj;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartupActivity extends AppCompatActivity {

    /* access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_start);
        LinearLayout ll =  findViewById(R.id.layout_logo);
        TextView salam = findViewById(R.id.ass);
        TranslateAnimation translateAnimation = new TranslateAnimation((float) 900, (float) 0, (float) 0, (float) 0);
        translateAnimation.setDuration(1300);
        translateAnimation.setFillAfter(true);
        salam.startAnimation(translateAnimation);
        TranslateAnimation translateAnimation2 = new TranslateAnimation((float) 0, (float) 0, (float) 1120, (float) 0);
        translateAnimation2.setDuration(1000);
        translateAnimation2.setFillAfter(true);
        ll.startAnimation(translateAnimation2);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {

                    Thread.sleep((long) 2000);
                } catch (Exception e) {

                } finally {

                    startActivity(new Intent(StartupActivity.this,
                            MainActivity.class));
                    finish();
                }
            };
        };
        splashTread.start();
    }

    /* access modifiers changed from: protected */
    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
