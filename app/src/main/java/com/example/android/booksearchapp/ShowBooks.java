package com.example.android.booksearchapp;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ShowBooks extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Book>> {
    private String searchUrl;
    private BookAdaptor mBookAdaptor;
    private TextView mEmptyStateTextView;
    private View loadingIndicator;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_item);


        Bundle bundle = getIntent().getExtras();
        keyWord = bundle.getString("message");


        ListView bookListView = (ListView) findViewById(R.id.list_haha);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        loadingIndicator = findViewById(R.id.progress_bar);

        mBookAdaptor = new BookAdaptor(this, new ArrayList<Book>());

        bookListView.setAdapter(mBookAdaptor);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
        Log.i("initLoader", "Loader1 initialed");



    }

    private String UrlBuilder(String keyWord) {
        StringBuilder urlBuilder = new StringBuilder("https://www.googleapis.com/books/v1/volumes?q=");
        try{String query = URLEncoder.encode(keyWord,"UTF-8");
            urlBuilder.append(query);}
        catch (UnsupportedEncodingException e){
            throw new AssertionError("Utf-8 is unknown");
        }
        urlBuilder.append("&maxResults=6");

        Log.e("ShowBooks,UrlBuilder",urlBuilder.toString());
        return urlBuilder.toString();
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        searchUrl = UrlBuilder(keyWord);
        return new BookLoader(ShowBooks.this, searchUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookList) {
        loadingIndicator.setVisibility(View.GONE);
        mBookAdaptor.clear();
        if (bookList != null && !bookList.isEmpty()) {
            mBookAdaptor.addAll(bookList);
        } else{
            mEmptyStateTextView.setText("No searching result");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }


}



