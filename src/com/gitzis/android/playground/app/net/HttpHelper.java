package com.gitzis.android.playground.app.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class HttpHelper {
    public static String readUrlToString(String urlString) {
        StringBuilder lines = new StringBuilder();
        int doRetry = 5;
        while (doRetry-- > 0) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    Log.wtf(HttpHelper.class.getName(), "Failed : HTTP error code : " + conn.getResponseCode());
                    Thread.sleep(1000);
                    continue;
                }
                doRetry = 0;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    lines.append(line);
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.wtf(HttpHelper.class.getName(), "wtf", e);
            }
        }
        return lines.toString();
    }

    public static void doPost() throws IOException {
        URL url = makeUrl();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //          writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //          readStream(in);
        } finally {
            urlConnection.disconnect();
        }
    }

    private static URL makeUrl() {
        URL url;
        try {
            url = new URL("localhost:8080/greeting");
        } catch (MalformedURLException e) {
            throw new RuntimeException("shouldn't happen");
        }
        return url;
    }
}
