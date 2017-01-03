package com.example.android.booksearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {
    private String keyWord;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Create the search notice text, textbox .*/
        TextView searchNotice = (TextView) findViewById(R.id.notice_text);
        searchNotice.setText(R.string.search);
        final EditText keyWordTextBox = (EditText) findViewById(R.id.search_key);
        /*and search button and a onclick listener*/
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                    keyWord = keyWordTextBox.getText().toString();
                    //Create the intent to the list Activity to show search result
                    Intent searchBookIntent = new Intent(MainActivity.this, ShowBooks.class);
                    //pass the String keyWord to another activity for request URL.
                    searchBookIntent.putExtra("message", keyWord);
                    startActivity(searchBookIntent);

            }
        });


    }
}
