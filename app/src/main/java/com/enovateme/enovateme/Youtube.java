
package com.enovateme.enovateme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.widget.ListPopupWindow.MATCH_PARENT;

public class Youtube extends AppCompatActivity {


    private View otherViews;

    private boolean fullscreen;

    public static final String EXTRA_TITLE = "videotitle";
    public static final String EXTRA_VIDEOID = "videoid";
    String title,videoId;
    TextView videoTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }


}