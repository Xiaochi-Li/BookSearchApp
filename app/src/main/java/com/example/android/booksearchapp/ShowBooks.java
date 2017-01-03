package com.example.android.booksearchapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static com.example.android.booksearchapp.R.string.search;

public class ShowBooks extends AppCompatActivity {
    private String searchUrl;
    private BookAdaptor mBookAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_item);


        Bundle bundle = getIntent().getExtras();
        String keyWord = bundle.getString("message");
        Log.e("ShowBooks", keyWord);
        searchUrl = UrlBuilder(keyWord);

        ListView bookListView = (ListView) findViewById(R.id.list_haha);

        mBookAdaptor = new BookAdaptor(this, new ArrayList<Book>());

        bookListView.setAdapter(mBookAdaptor);

        BookAsyncTask task = new BookAsyncTask();
        task.execute(searchUrl);
    }

    private String UrlBuilder(String keyWord) {
        StringBuilder urlBuilder = new StringBuilder("https://www.googleapis.com/books/v1/volumes?q=");
        urlBuilder.append(keyWord);
        urlBuilder.append("&maxResults=6");
        return urlBuilder.toString();
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Book> result = QuerryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> data) {
            mBookAdaptor.clear();

            if (data != null && !data.isEmpty()) {
                mBookAdaptor.addAll(data);
            }
        }
    }


}
