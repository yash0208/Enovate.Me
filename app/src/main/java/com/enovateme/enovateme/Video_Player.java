package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import static maes.tech.intentanim.CustomIntent.customType;

public class Video_Player extends AppCompatActivity {
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__player);

        LinearLayout container1=findViewById(R.id.container);
        AdView adView;
        adView =new AdView(this,"227638281969200_227643145302047", AdSize.BANNER_HEIGHT_50);
        container1.addView(adView);
        adView.loadAd();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        Intent i=getIntent();
        link=i.getStringExtra("Link");
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {
                super.onApiChange(youTubePlayer);
                youTubePlayer.loadVideo(link,0);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }
}
