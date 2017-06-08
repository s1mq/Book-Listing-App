package com.example.android.booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An {@link BookAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Book} objects.
 *
 * These list item layouts will be provided to an adapter view like ListView to be displayed
 * to the user.
 */

public class BookAdapter extends ArrayAdapter<Book>{

    private final static String AUTHOR = "Author: ";

    public BookAdapter (Activity context, ArrayList<Book> books){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0,books);
    }

    private static class ViewHolder{
        ImageView thumbnail;
        TextView title;
        TextView author;
        TextView publisher;
        TextView category;
        TextView pageCount;
        TextView rating;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail);
            holder.title = (TextView) listItemView.findViewById(R.id.title);
            holder.author = (TextView) listItemView.findViewById(R.id.author);
            holder.publisher = (TextView) listItemView.findViewById(R.id.publisher);
            holder.category = (TextView) listItemView.findViewById(R.id.category);
            holder.pageCount = (TextView) listItemView.findViewById(R.id.page_count);
            holder.rating = (TextView) listItemView.findViewById(R.id.rating);
            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder)listItemView.getTag();
        }
        Book currentBook = getItem(position);

        holder.title.setText(currentBook.getTitle());
        holder.author.setText(currentBook.getAuthor());
        holder.publisher.setText(currentBook.getPublisher());
        holder.category.setText(currentBook.getCategory());
        holder.pageCount.setText(currentBook.getPageCount());
        holder.rating.setText(currentBook.getRating());

        return listItemView;
    }
}
