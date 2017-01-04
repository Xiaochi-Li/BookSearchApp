package com.example.android.booksearchapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {
    private String keyWord;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Create the search notice text, textbox .*/
        final TextView searchNotice = (TextView) findViewById(R.id.notice_text);
        searchNotice.setText(R.string.search);
        final EditText keyWordTextBox = (EditText) findViewById(R.id.search_key);
        /*and search button and a onclick listener*/
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                keyWord = keyWordTextBox.getText().toString();
                //Create the intent to the list Activity to show search result
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Intent searchBookIntent = new Intent(MainActivity.this, ShowBooks.class);
                    //pass the String keyWord to another activity for request URL.
                    searchBookIntent.putExtra("message", keyWord);
                    startActivity(searchBookIntent);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "No internet connection";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }
        });


    }
}
