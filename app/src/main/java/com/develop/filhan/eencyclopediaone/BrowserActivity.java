package com.develop.filhan.eencyclopediaone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

/*
* Activity untuk menampilkan WebView atau Link yang tuju melalui perantara Browser
*/
public class BrowserActivity extends AppCompatActivity {

    //Key Activity
    public static final String EXTRA_URL_ADDRESS = "URL_ADDRESS_GSEARCH";

    //WebView Component
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        //Initiate WebView COmponent
        webView=(WebView)findViewById(R.id.webview1);

        //Get Intent
        String url = getIntent().getStringExtra(EXTRA_URL_ADDRESS);

        //Setting WebView
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Load URL Tujuan
        webView.loadUrl(url);

        Toast.makeText(this, "Tap \"Back Button\" for Detail Menu", Toast.LENGTH_SHORT).show();
    }
}
