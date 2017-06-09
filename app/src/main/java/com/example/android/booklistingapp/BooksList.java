package com.example.android.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class BooksList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = BooksList.class.getName();

    /**
     * Adapter for the list of earthquakes
     */
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    /**
     * Helper method to build the final query URL
     *
     * @return a URL with the user's input and a max search result of 30 books
     */
    private String urlHelper() {
        // Get the user input on the EditText
        String searchBox = getIntent().getStringExtra("TITLE");
        // Set the maximum results to 30
        String queryMax = "&maxResults=30";
        // Create new request URL
        String newUrl = BOOK_REQUEST_URL + searchBox + queryMax;
        return newUrl;
    }


    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, urlHelper());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(GONE);

        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText(R.string.no_books);

        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Find a reference to the {@link TextView) in the layout
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        // Set the empty state Textview on the activity
        bookListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Book currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data.
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.i(LOG_TAG, "TEST: calling initLoader() ...");
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }
}
