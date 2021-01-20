package com.jrmod.app.makhroj;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static long back_pressed;
    private ActionBarDrawerToggle drawerToggle;
    private Activity mActivity;
    private Button mButtonPlay;
    private Button mButtonStop;
    private Context mContext;
    private DrawerLayout mDrawer;
    private TextView mDue;
    private TextView mDuration;
    private Handler mHandler;
    private TextView mPass;
    private MediaPlayer mPlayer;
    private Runnable mRunnable;
    private SeekBar mSeekBar;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    private TextView tt;
    private WebView webview;

    // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.BaseFragmentActivityDonut, android.app.Activity
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        
        mButtonPlay = findViewById(R.id.btn_play);
        mButtonPlay.setText("Play");
        mButtonStop = findViewById(R.id.btn_stop);
        mSeekBar = findViewById(R.id.seekbar);
        mPass = findViewById(R.id.tv_pass);
        mDuration = findViewById(R.id.tv_duration);
        mDue = findViewById(R.id.tv_due);

        mHandler = new Handler();
        mPlayer = MediaPlayer.create(this, (int) R.raw.nadlom);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override 
            public void onCompletion(MediaPlayer mediaPlayer) {
                mButtonPlay.setText("Play");
            }
        });
        mDuration.setText("Putar Audio By Ustadz Abdul Rouf");
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    mButtonPlay.setText("Play");
                } else {
                    mPlayer.start();
                    mButtonPlay.setText("Pause");
                }
                initializeSeekBar();
            }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (mPlayer != null && z) {
                    mPlayer.seekTo(i * 1000);
                }
            }
        });
        webview = findViewById(R.id.wv);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.loadUrl("file:///android_asset/intro.html");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);
        tt = findViewById(R.id.toolbar_title);
        tt.setText(R.string.pengertian);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        nvDrawer = findViewById(R.id.nav_view);
        setupDrawerContent(nvDrawer);
        mDrawer.setDrawerListener(drawerToggle);
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.app_name, R.string.app_name);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            final LinearLayout linear = findViewById(R.id.audioControl);
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }

            private void selectDrawerItem(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_a /* 2131165336 */:
                        hidelayout();
                        tt.setText(R.string.jouf);
                        webview.loadUrl("file:///android_asset/jouf.html");
                        break;
                    case R.id.nav_b /* 2131165337 */:
                        hidelayout();
                        webview.loadUrl("file:///android_asset/halqi.html");
                        tt.setText(R.string.halqi);
                        break;
                    case R.id.nav_c /* 2131165338 */:
                        hidelayout();
                        webview.loadUrl("file:///android_asset/lisan.html");
                        tt.setText(R.string.lisan);
                        break;
                    case R.id.nav_d /* 2131165339 */:
                        hidelayout();
                        webview.loadUrl("file:///android_asset/bibir.html");
                        tt.setText(R.string.bibir);
                        break;
                    case R.id.nav_e /* 2131165340 */:
                        hidelayout();
                        webview.loadUrl("file:///android_asset/khoisum.html");
                        tt.setText(R.string.khoisum);
                        break;
                    case R.id.nav_f /* 2131165341 */:
                        webview.loadUrl("file:///android_asset/nasyid.html");
                        tt.setText(R.string.nasyid);
                        linear.setVisibility(View.VISIBLE);
                        break;
                    case R.id.sub_thanks /* 2131165342 */:
                        nilai();
                        break;
                    case R.id.sub_about /* 2131165343 */:

                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.about);
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override // java.lang.Runnable
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 10000);
                        break;
                    default:
                        hidelayout();
                        tt.setText(R.string.pengertian);
                        webview.loadUrl("file:///android_asset/intro.html");
                        break;
                }
                mDrawer.closeDrawers();
            }

            private void hidelayout() {
                linear.setVisibility(View.GONE);
                mPlayer.pause();
                mButtonPlay.setText("Play");
            }

            private void nilai() {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.jrmod.app.tajwid")));
                } catch (Exception e) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.jrmod.app.tajwid")));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + ((long) 2000) > System.currentTimeMillis()) {
            super.onBackPressed();
        } else if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else {
            Toast.makeText(getBaseContext(), "Tekan sekali lagi untuk keluar!", Toast.LENGTH_LONG).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    /* access modifiers changed from: protected */
    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        drawerToggle.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    /* access modifiers changed from: protected */
    public void getAudioStats() {
        int duration = mPlayer.getDuration() / 1000;
        int duration2 = (mPlayer.getDuration() - mPlayer.getCurrentPosition()) / 1000;
        mPass.setText(new StringBuffer().append(new StringBuffer().append("").append(duration - duration2).toString()).append(" detik").toString());
        mDuration.setText(new StringBuffer().append(new StringBuffer().append("").append(duration).toString()).append(" detik").toString());
        mDue.setText(new StringBuffer().append(new StringBuffer().append("").append(duration2).toString()).append(" detik").toString());
    }

    /* access modifiers changed from: protected */
    public void initializeSeekBar() {
        mSeekBar.setMax(mPlayer.getDuration() / 1000);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mPlayer != null) {
                    mSeekBar.setProgress(mPlayer.getCurrentPosition() / 1000);
                    getAudioStats();
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }
}
