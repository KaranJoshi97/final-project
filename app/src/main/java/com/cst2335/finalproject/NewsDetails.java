package com.cst2335.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NewsDetails extends Activity {

    TextView textViewTitle;
    TextView textViewAuthor;
    TextView textViewDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        textViewDescription  = findViewById(R.id.textViewDescription);


        Intent i = this.getIntent();
        Bundle b = i.getExtras();

        textViewTitle .setText (b.getString( "title", "?"));
        textViewAuthor.setText (b.getString( "author", "?") );
        textViewDescription.setText (b.getString( "description", "?") );

    }
}


