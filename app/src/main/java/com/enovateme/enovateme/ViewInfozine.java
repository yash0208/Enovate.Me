package com.enovateme.enovateme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import static maes.tech.intentanim.CustomIntent.customType;

public class ViewInfozine extends AppCompatActivity {
    WebView webView;
    String link;
    String Name;
    TextView tittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_infozine);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        LinearLayout container1=findViewById(R.id.container);
        AdView adView;
        adView =new AdView(this,"227638281969200_227643145302047", AdSize.BANNER_HEIGHT_50);
        container1.addView(adView);
        adView.loadAd();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        webView=findViewById(R.id.web);
        Intent i=getIntent();
        link=i.getStringExtra("Link");
        Name=i.getStringExtra("Name");
        tittle=findViewById(R.id.name1);
        tittle.setText(Name);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        String doc="<iframe src="+link+" width='100%' height='100%' style='border: none;'></iframe>";
        webView.loadData( doc , "text/html", "UTF-8");
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
    }
}
