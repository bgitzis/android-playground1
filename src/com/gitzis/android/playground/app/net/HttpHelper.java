package com.gitzis.android.playground.app.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import android.util.Log;

public class HttpHelper {
    public static String doGet(String urlString) {
        StringBuilder lines = new StringBuilder();
        int doRetry = 5;
        while (doRetry-- > 0) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    logNetworkError(conn);
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

    public static String doPost(String content) {
        URL url = makeUrl();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setChunkedStreamingMode(0);
            conn.setRequestMethod("POST");
            //            conn.setRequestProperty("Accept", "application/json");
            //            conn.setRequestProperty("Content-Type", "text/plain; charset=utf8");
            Writer out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            out.write("string=" + content);
            out.close();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                logNetworkError(conn);
                return "";
            }
            String response = readToString(conn.getInputStream());
            return response;
        } catch (Exception e) {
            Log.e(HttpHelper.class.getName(), e.getMessage(), e);
            return "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static void logNetworkError(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
            sb.append(header.getKey()).append("=").append(header.getValue()).append("\n");
        }
        Log.e(HttpHelper.class.getName(), sb.toString());
    }

    private static String readToString(InputStream is) throws UnsupportedEncodingException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    private static URL makeUrl() {
        URL url;
        try {
            url = new URL("http://192.168.0.103:8080/post");
        } catch (MalformedURLException e) {
            Log.wtf(HttpHelper.class.getName(), e);
            throw new RuntimeException("shouldn't happen", e);
        }
        return url;
    }
}
