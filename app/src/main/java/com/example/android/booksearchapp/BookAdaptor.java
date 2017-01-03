package com.example.android.booksearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lixiaochi on 30/12/16.
 */

public class BookAdaptor extends ArrayAdapter<Book> {
    public BookAdaptor(Context context, List<Book> books){
        super(context,0,books);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).
                    inflate(R.layout.activity_show_books,parent,false);
        }

        Book currentBook = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentBook.getAuthor());

        TextView descriptions = (TextView) listItemView.findViewById(R.id.description);
        descriptions.setText(currentBook.getDescription());

        return listItemView;
    }
}
