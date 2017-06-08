package com.example.android.booklistingapp;

import static android.R.attr.author;

/**
 * A custom class for books to store information about them, e.g title, author, page count etc.
 */

public class Book {

    /**
     * Title of the book
     */
    private String mTitle;

    /**
     * Author of the book
     */
    private String mAuthor;

    /**
     * Category of the book
     */
    private String mCategory;

    /**
     * Page count fo the book
     */
    private int mPageCount;

    /**
     * Rating of the book
     */
    private int mRating;

    /**
     * Web url of the book
     */
    private String mUrl;

    /**
     * Public constructor to make a new {@link Book} object.
     *
     * @param title     is the title of the book
     * @param author    is/are the author(s) of the book
     * @param category  is the category of the book
     * @param pageCount is the number of pages of the book
     * @param rating    is the book's rating
     * @param url       is the address for the book
     */
    public Book(String title, String author, String category, int pageCount, int rating, String url) {
        mTitle = title;
        mAuthor = author;
        mCategory = category;
        mPageCount = pageCount;
        mRating = rating;
        mUrl = url;
    }

    /**
     * Getter methods to return the states of the book object.
     *
     * @return corresponding state
     */

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getCategory() {
        return mCategory;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public int getRating() {
        return mRating;
    }

    public String getUrl() {
        return mUrl;
    }
}


