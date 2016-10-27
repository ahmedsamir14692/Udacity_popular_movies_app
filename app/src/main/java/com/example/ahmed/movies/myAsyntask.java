package com.example.ahmed.movies;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

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
 * Created by ahmed on 7/31/16.
 */
public class myAsyntask extends AsyncTask<String,Void,ArrayList<movies>> {
    int z;
    ArrayAdapter<movies> arrayAdapter;
    AdapterView<ListAdapter> viewGroup;
    Context context;
    Activity activity;
    comunicator comunicator;
    ProgressBar progressBar;
    static ArrayList<movies> parce=new ArrayList<movies>();
  public  myAsyntask(int i, ArrayAdapter<movies> arrayAdapter, AdapterView<ListAdapter> viewGroup, Context context, Activity activity, ProgressBar progressBar)
    {
        this.viewGroup=viewGroup;
        z=i;
this.arrayAdapter=arrayAdapter;
        this.context=context;
        this.activity=activity;
        this.comunicator= (com.example.ahmed.movies.comunicator) activity;
        this.progressBar=progressBar;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressBar.setVisibility(View.VISIBLE);
        Log.d("hhh","hhhhhhhhhhhhhhh pre excute ");

    }

    @Override
    protected ArrayList<movies> doInBackground(String... voids) {
        InputStreamReader inputStreamReader=null;
        HttpURLConnection httpURLConnection=null;
        if(utility.marker==5)
            Log.d("hhh","hhhhhhhhhhhhhhh0000000");
        InputStream inputStream=null;
        StringBuffer stringBuffer=new StringBuffer();
        BufferedReader bufferedReader=null;
        try {
            URL url =new URL(voids[0]);
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
        Log.d("hhh","hhhhhhhhhhhhhhh1");
        ArrayList<movies> arrayofmovies =new ArrayList<movies>();
        if(!jason.isEmpty())
        {
            Log.d("hhh","hhhhhhhhhhhhhhh2");
            if(utility.marker==7)
            {
                SharedPreferences sharedPreferences=activity.getSharedPreferences("refresh",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sharedPreferences.edit();
                ed.putString("data",jason);
                ed.apply();
            }
            if(utility.marker==5)
            {
                SharedPreferences sharedPreferences=activity.getSharedPreferences("toprated",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sharedPreferences.edit();
                ed.putString("data",jason);
                ed.apply();
            }

            try {
                JSONObject jsonObject=new JSONObject(jason);
                JSONArray moviesaray=jsonObject.getJSONArray("results");
                for(int i=0;i<moviesaray.length();i++)
                {
                    movies movies=new movies();
                    movies.setImage_of_poster(moviesaray.getJSONObject(i).getString("poster_path"));


    movies.setOrgilal_title(moviesaray.getJSONObject(i).getString("original_title"));
    movies.setOver_view(moviesaray.getJSONObject(i).getString("overview"));
    movies.setRate(moviesaray.getJSONObject(i).getDouble("vote_average"));
    movies.setRelase_date(moviesaray.getJSONObject(i).getString("release_date"));
movies.setId(moviesaray.getJSONObject(i).getInt("id"));

arrayofmovies.add(movies);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        return arrayofmovies;

    }

    @Override
    protected void onPostExecute(final ArrayList<movies> movies) {
        super.onPostExecute(movies);
        if(!movies.isEmpty())
        {

            parce=movies;
            arrayAdapter.clear();
            arrayAdapter.addAll(movies);

progressBar.setVisibility(View.INVISIBLE);
            viewGroup.setAdapter(arrayAdapter);
viewGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String[] array={movies.get(i).getImage_of_poster(),movies.get(i).getOrgilal_title(),

                movies.get(i).getOver_view(),movies.get(i).getRelase_date(),new Double(movies.get(i).getRate()).toString(),
new Integer(movies.get(i).getId()).toString(),

        };

        utility.go_to_movie_detail(array,myAsyntask.this.activity,myAsyntask.this.context,comunicator);


} });

        }
        else{

            Toast toast=Toast.makeText(context,"there is no internet connection plz check your connection and if you can see any data this may be an old data",Toast.LENGTH_LONG);
            toast.show();
            progressBar.setVisibility(View.INVISIBLE);

if(utility.marker==7)
{

    SharedPreferences sharedPreferences=activity.getSharedPreferences("refresh",Context.MODE_PRIVATE);

    String jason =sharedPreferences.getString("data",null);
    if(jason!=null)
    {
        utility.two(utility.take_string_give_array_of_movies(jason),parce,arrayAdapter,viewGroup,progressBar,activity,context,comunicator);

    }

}
            if(utility.marker==5)
            {
                SharedPreferences sharedPreferences=activity.getSharedPreferences("toprated",Context.MODE_PRIVATE);

                String jason =sharedPreferences.getString("data",null);
                if(jason!=null)
                {
                    utility.two(utility.take_string_give_array_of_movies(jason),parce,arrayAdapter,viewGroup,progressBar,activity,context,comunicator);

                }

            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


}


