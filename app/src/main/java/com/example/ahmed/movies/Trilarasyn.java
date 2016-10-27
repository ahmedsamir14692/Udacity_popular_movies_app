package com.example.ahmed.movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ahmed on 8/2/16.
 */
public class Trilarasyn extends AsyncTask<String,Void,ArrayList<trailaar>> {

    ArrayAdapter<trailaar> arrayAdapter;
    AdapterView<ListAdapter> viewGroup;
    Context context;
    public Trilarasyn( ArrayAdapter<trailaar> arrayAdapter, AdapterView<ListAdapter> viewGroup,Context context)
    {
        this.viewGroup=viewGroup;

        this.arrayAdapter=arrayAdapter;
        this.context=context;
    }

    @Override
    protected ArrayList<trailaar> doInBackground(String... strings) {
        InputStreamReader inputStreamReader=null;
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        StringBuffer stringBuffer=new StringBuffer();
        BufferedReader bufferedReader=null;
        try {
            URL url =new URL(strings[0]);
            httpURLConnection= (HttpURLConnection) url.openConnection();
            inputStream=httpURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(line+"\n");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(inputStreamReader!=null)
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(bufferedReader!=null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(httpURLConnection !=null)
                httpURLConnection.disconnect();
        }
        String jason=stringBuffer.toString();
        ArrayList<trailaar> arrayoftailers =new ArrayList<trailaar>();
        if(!jason.isEmpty())
        {
            try {
                JSONObject jsonObject=new JSONObject(jason);
                JSONArray trailarsaray=jsonObject.getJSONArray("results");
                for(int i=0;i<trailarsaray.length();i++)
                {
                    trailaar trailaar=new trailaar();
trailaar.setKey(trailarsaray.getJSONObject(i).getString("key"));

                    trailaar.setName(trailarsaray.getJSONObject(i).getString("name"));



                    arrayoftailers.add(trailaar);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return arrayoftailers;
    }

    @Override
    protected void onPostExecute(final ArrayList<trailaar> trailaars) {
        super.onPostExecute(trailaars);

        super.onPostExecute(trailaars);
        if(!trailaars.contains(null)) {
            arrayAdapter.clear();
            arrayAdapter.addAll(trailaars);
            viewGroup.setAdapter(arrayAdapter);
            viewGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Uri uri= Uri.parse("https://www.youtube.com/watch?").buildUpon().appendQueryParameter("v",trailaars.get(i).getKey())
                            .build();
                   Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    Trilarasyn.this.context.startActivity(intent);

                }
            });
        }

        }


}
