package com.example.android.booksearchapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by lixiaochi on 3/1/17.
 */

public class BookLoader extends AsyncTaskLoader {
    private String mUrl;
    private BookLoader(Context context,String url){
        super(context);
        mUrl= url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {

        if (mUrl == null){
            return null;
        }
        return QuerryUtils.fetchEarthquakeData(mUrl);
    }

}
