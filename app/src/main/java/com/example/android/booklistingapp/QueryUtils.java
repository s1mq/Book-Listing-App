package com.example.android.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.android.booklistingapp.BooksList.LOG_TAG;


/**
 * Helper methods related to requesting and receiving books data from Google Books.
 */
public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Google books and return a list of {@link Book} objects.
     */
    public static List<Book> fetchEarthquakeData(String requestUrl) {

        // Create URL object
        URL url = createURL(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<Book> books = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the reponse was successful (response code 200),
            // then read the input sream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies that an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Book> extractFeatureFromJson(String bookJSON) {
        // If the JSON string is empty ot null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response String. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON reponse String
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of items (or books).
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            // For each book in the bookArray, create an {@link Book} object
            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single book at position i within the list of books
                JSONObject currentBook = bookArray.getJSONObject(i);

                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a list of all properties
                // for that book.
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                // Extract the value for the key called "title"
                String title = volumeInfo.getString("title");

                // Create a new ArrayList
                JSONArray authorsArray;
                // Extract the value for the key called "authors"
                StringBuilder authors = new StringBuilder();
                if (volumeInfo.has("authors")) {
                    authorsArray = volumeInfo.getJSONArray("authors");
                    for (int n = 0; n < authorsArray.length(); n++) {
                        authors.append(authorsArray.getString(n));
                        authors.append(" ");
                    }
                } else {
                    authors.append("Not available");
                }

                // Extract the value for the key called "publisher"
                String publisher;
                if (volumeInfo.has("publisher")) {
                    publisher = volumeInfo.getString("publisher");
                } else {
                    publisher = "Not available";
                }


                // Create a new ArrayList
                JSONArray categoryArray;
                // Extract the value for the key called "categories"
                StringBuilder categories = new StringBuilder();
                if (volumeInfo.has("categories")) {
                    categoryArray = volumeInfo.getJSONArray("categories");
                    for (int n = 0; n < categoryArray.length(); n++) {
                        categories.append(categoryArray.getString(n));
                        authors.append(" ");
                    }
                } else {
                    categories.append("Not available");
                }

                // Extract the value for key called "pageCount"
                String pageCount;
                if (volumeInfo.has("pageCount")) {
                    pageCount = volumeInfo.getString("pageCount");
                } else {
                    pageCount = "Not available";
                }

                // Extract the value for key called "averageRating"
                String rating;
                if (volumeInfo.has("averageRating")) {
                    rating = volumeInfo.getString("averageRating");
                } else {
                    rating = "--";
                }

                // Extract the value for the key called "infoLink"
                String url = null;
                if (volumeInfo.has("infoLink")) {
                    url = volumeInfo.getString("infoLink");
                }

                // Create a new Book java object from title, authors, publisher, categories,
                // pageCount, rating and url from the JSON response
                Book book = new Book(title, authors, publisher, categories, pageCount, rating, url);

                // Add earthquake to list of earthquakes
                books.add(book);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return books;
    }


}