package com.example.ahmed.movies;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

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
import java.util.ArrayList;

/**
 * Created by ahmed on 8/3/16.
 */
public class reviewasyn extends AsyncTask <String,Void,String> {

    ArrayAdapter<review> arrayAdapter;
    AdapterView<ListAdapter> viewGroup;
    Context context;
    TextView textView;
    public reviewasyn( TextView textView, Context context)
    {
        this.viewGroup=viewGroup;

        this.arrayAdapter=arrayAdapter;
        this.context=context;
        this.textView=textView;
    }
    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buffer5=new StringBuffer();
        InputStreamReader inputStreamReader = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (inputStreamReader != null)
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        String jason = stringBuffer.toString();

        ArrayList<review> arrayofreviews = new ArrayList<review>();
        if (!jason.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(jason);
                JSONArray reviewsaray = jsonObject.getJSONArray("results");
                for (int i = 0; i < reviewsaray.length(); i++) {
                    review review = new review();
                    review.setAuthor(reviewsaray.getJSONObject(i).getString("author"));review.setContent(reviewsaray.getJSONObject(i).getString("content"));
                   String wreiter= reviewsaray.getJSONObject(i).getString("author")+" :   ";
                    String thereview=reviewsaray.getJSONObject(i).getString("content")+"\n";
String tot=wreiter+thereview;
                    buffer5.append(tot+"\n");

                    arrayofreviews.add(review);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return buffer5.toString();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
textView.setText(s);
    }
}