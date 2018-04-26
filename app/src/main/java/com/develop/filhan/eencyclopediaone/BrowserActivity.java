package com.develop.filhan.eencyclopediaone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class BrowserActivity extends AppCompatActivity {

    public static final String EXTRA_URL_ADDRESS = "URL_ADDRESS_GSEARCH";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        webView=(WebView)findViewById(R.id.webview1);

        String url = getIntent().getStringExtra(EXTRA_URL_ADDRESS);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);

        Toast.makeText(this, "Tap \"Back Button\" for Detail Menu", Toast.LENGTH_SHORT).show();
    }
}
