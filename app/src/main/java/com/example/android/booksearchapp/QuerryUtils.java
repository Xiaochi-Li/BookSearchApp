package com.example.android.booksearchapp;

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
import java.util.List;



public class QuerryUtils {

    //Tag for the log messages
    private static final String LOG_TAG = QuerryUtils.class.getSimpleName();

    //Create a private constructor because no one should ever* Create a private constructor because no one should ever create a {@link QueryUtils} object.
    // This class is only meant to hold static variables and methods, which can be accessed
    // directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).

    private QuerryUtils() {
    }

    public static List<Book> fetchEarthquakeData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

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
            int haha = urlConnection.getResponseCode();
            System.out.print(haha);
            Log.e("makeHttpRequest", Integer.toString(urlConnection.getResponseCode()));

            //if the request was successful (if the response code 200),
            //then read the input string and parse the response.

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

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

    private static List<Book> extractFeatureFromJson(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            Log.e("extractFeatureFromJson", " is excuted");
            //Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            //Extract the JSONArray associated with the key called "items",
            //Which represents a list of items (books)
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");
            //For each book in the  bookArray, create an {@link book} object
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");
                String author;
                StringBuilder authorsBuilder = new StringBuilder("");


                //if there is no author just put empty string.
                if (volumeInfo.has("authors")) {
                    JSONArray authorsJSONArray = volumeInfo.getJSONArray("authors");
                    for (int o = 0; o < authorsJSONArray.length(); o++) {

                        authorsBuilder.append(authorsJSONArray.getString(o) + ", ");
                    }
                    author = authorsBuilder.toString();
                } else {
                    author = "";
                }

//check if there are description
                String description;
                if (volumeInfo.has("description")) {
                    description = volumeInfo.getString("description");
                } else {
                    description = "";
                }

                Book book = new Book(title, author, description);
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e("QuerryUtils", "Problem parsing the book JSON results", e);
        }
        return books;
    }
}
