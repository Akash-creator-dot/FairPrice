package com.example.fairprice;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LocationFetcher {

    public void getLocationAsync(String location, LocationCallback callback) {
        // Execute the network request on a background thread
        new FetchLocationTask(callback).execute(location);
    }

    // AsyncTask to handle the network operation in the background
    private static class FetchLocationTask extends AsyncTask<String, Void, String> {
        private LocationCallback callback;

        FetchLocationTask(LocationCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params) {
            String location = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader in = null;
            try {
                // OpenStreetMap Nominatim API URL for geocoding
                String apiUrl = "https://nominatim.openstreetmap.org/search?q=" + location + "&format=json&addressdetails=1";
                URL url = new URL(apiUrl);
                urlConnection = (HttpURLConnection) url.openConnection();

                // Adding User-Agent header as required by Nominatim API
                urlConnection.setRequestProperty("User-Agent", "FairPriceApp/1.0 (contact@example.com)");

                // Open connection and get the response
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Check if the response is not empty
                if (response.length() > 0) {
                    String jsonResponse = response.toString();
                    // Example of extracting the first match's display_name from the response
                    if (jsonResponse.contains("display_name")) {
                        int startIndex = jsonResponse.indexOf("\"display_name\":\"") + 16;
                        int endIndex = jsonResponse.indexOf("\"", startIndex);
                        return jsonResponse.substring(startIndex, endIndex); // Returning the location name
                    }
                }

                return null; // Return null if no location found or response is empty

            } catch (IOException e) {
                e.printStackTrace();
                return null; // Return null in case of errors
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Pass the result back to the callback
            if (callback != null) {
                callback.onLocationFetched(result);
            }
        }
    }

    // Callback interface to return the result of the background task
    public interface LocationCallback {
        void onLocationFetched(String location);
    }
}
