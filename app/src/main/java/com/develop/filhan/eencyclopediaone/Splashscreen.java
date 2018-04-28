package com.develop.filhan.eencyclopediaone;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Splashscreen extends AppCompatActivity {

    private TextView lblQuoteTXT, lblQuoteAuthor;
    private ImageView iconLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        lblQuoteTXT = (TextView) findViewById(R.id.lblSplashQuoteText);
        lblQuoteAuthor = (TextView) findViewById(R.id.lblSplashQuoteAuthor);

        ArrayList<String> quotes = new ArrayList<>();
        quotes.add("Food is our common ground, a universal experience|James Beard");
        quotes.add("People want honest, flavourful food, not some show-off meal that takes days to prepare|Ted Allen");
        quotes.add("Good food ends with good talk|Geoffrey Neighor");
        quotes.add("You don’t need a silver fork to eat good food|Paul Prudhomme");
        quotes.add("Food is symbolic of love when words are inadequate|Alan D. Wolfelt");
        quotes.add("Tell me what you eat, and I will tell you who you are|Jean Anthelme Brillat-Savarin");

        int min = 0, max = quotes.size() - 1;
        int index = (min + (int) (Math.random() * (max - min)));
        String[] quote = quotes.get(index).split("\\|");

        lblQuoteTXT.setText("\"" + quote[0] + "\"");
        lblQuoteAuthor.setText(Html.fromHtml("&mdash;&nbsp; " + quote[1] + " &nbsp;&mdash;"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        }, 6000);
    }
}
