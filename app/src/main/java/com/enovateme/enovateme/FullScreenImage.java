package com.enovateme.enovateme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.github.chrisbanes.photoview.PhotoView;
import com.zolad.zoominimageview.ZoomInImageView;

import static maes.tech.intentanim.CustomIntent.customType;

public class FullScreenImage extends AppCompatActivity implements View.OnTouchListener {
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    float initialX, initialY;
    String link;
    private PhotoView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_image);
        LinearLayout container1=findViewById(R.id.container);
        AdView adView;
        adView =new AdView(this,"227638281969200_227643145302047", AdSize.BANNER_HEIGHT_50);
        container1.addView(adView);
        adView.loadAd();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        imageView=findViewById(R.id.imageView);
        Intent i=getIntent();
        link=i.getStringExtra("Link");

        Glide.with(getApplicationContext()).load(link).fitCenter().into(imageView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                initialY = event.getY();


                // Log.d(TAG, "Action was DOWN");
                break;

            case MotionEvent.ACTION_MOVE:

                //Log.d(TAG, "Action was MOVE");
                break;

            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                float finalY = event.getY();


                //Log.d(TAG, "Action was UP");

                if (initialX < finalX) {
                    // Log.d(TAG, "Left to Right swipe performed");

                    onBackPressed();
                }

                if (initialX > finalX) {
                    // Log.d(TAG, "Right to Left swipe performed");
                    onBackPressed();
                }

                if (initialY < finalY) {
                    // Log.d(TAG, "Up to Down swipe performed");
                    onBackPressed();
                }

                if (initialY > finalY) {
                    // Log.d(TAG, "Down to Up swipe performed");
                    onBackPressed();
                }

                break;

            case MotionEvent.ACTION_CANCEL:
                //Log.d(TAG,"Action was CANCEL");
                break;

            case MotionEvent.ACTION_OUTSIDE:
                // Log.d(TAG, "Movement occurred outside bounds of current screen element");
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    public void back(View view) {
        onBackPressed();
    }
}