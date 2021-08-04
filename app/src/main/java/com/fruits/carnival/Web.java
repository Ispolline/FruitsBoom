package com.fruits.carnival;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        web = findViewById(R.id.web);
        web.loadUrl("https://vulcan-officialgames.site/bq8nbTHn");
        web.setWebViewClient(new WebViewClient());
        WebSettings webSettings = web.getSettings();
        CookieManager.getInstance().setAcceptCookie(true);
        webSettings.setJavaScriptEnabled(true);

        //new
        webSettings.setAllowContentAccess(true);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
    }


    @Override
    public void onBackPressed() {
        if (web.canGoBack()){
            web.goBack();
        }else{
            super.onBackPressed();
        }

    }
}