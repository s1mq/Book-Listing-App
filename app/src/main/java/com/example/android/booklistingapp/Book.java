package com.example.android.booklistingapp;

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
    private StringBuilder mAuthors;

    /**
     * Publisher of the book
     */
    private String mPublisher;

    /**
     * Category of the book
     */
    private StringBuilder mCategories;

    /**
     * Page count fo the book
     */
    private String mPageCount;

    /**
     * Rating of the book
     */
    private String mRating;

    /**
     * Web url of the book
     */
    private String mUrl;

    /**
     * Public constructor to make a new {@link Book} object.
     *
     * @param title         is the title of the book
     * @param authors       are the authors of the book
     * @param publisher     is the publisher of the book
     * @param categories    are the category of the book
     * @param pageCount     is the number of pages of the book
     * @param rating        is the book's rating
     * @param url           is the address for the book
     */
    public Book(String title, StringBuilder authors, String publisher, StringBuilder categories,
                String pageCount, String rating, String url) {
        mTitle = title;
        mAuthors = authors;
        mPublisher = publisher;
        mCategories = categories;
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

    public StringBuilder getAuthor() {
        return mAuthors;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public StringBuilder getCategory() {
        return mCategories;
    }

    public String getPageCount() {
        return mPageCount;
    }

    public String getRating() {
        return mRating;
    }

    public String getUrl() {
        return mUrl;
    }
}


