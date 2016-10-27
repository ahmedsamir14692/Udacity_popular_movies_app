package com.example.ahmed.movies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmed on 8/3/16.
 */
public class utility {
    public static int marker;


    public static void go_to_movie_detail(String[] array, Activity activity, Context context , comunicator comunicator)
    {
// take array of information and check if tablet or mobile phone :)
        Bundle b=new Bundle();
        b.putStringArray("infodetails", array);


        if(activity.findViewById(R.id.frame)==null)
        {
            Intent intent1=new Intent(context, Details.class);
            intent1.putExtras(b);
            context.startActivity(intent1);}
        if(activity.findViewById(R.id.frame)!=null) {

            comunicator.changebundle(b);
           activity.getFragmentManager().beginTransaction().replace(R.id.frame,new frameent_of_details()).commit();
            FrameLayout frameLayout= (FrameLayout) activity.findViewById(R.id.frame);
            frameLayout.setVisibility(View.VISIBLE);

        }


    }

    public static   int fix( ImageView imageView,GridView gridView,Context context)
    {
        // make the width of image suitable of this mobile
        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        if((screenWidthDp)<(2*185))
        {

            if(screenWidthDp>=(2*154))
            {
                imageView.getLayoutParams().width=(int)(convertDpToPixel(154,context));
                (gridView).setColumnWidth((int)(convertDpToPixel(154,context)));
                return 154;

            }
            else {
                imageView.getLayoutParams().width=(int)(convertDpToPixel(92,context));
                (gridView).setColumnWidth((int)(convertDpToPixel(92,context)));
                return 92;}
        }
        return 185;
    }
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

public static  ArrayList<movies> take_string_give_array_of_movies(String jason)
{
    ArrayList<movies> arrayofmovies =new ArrayList<movies>();
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



    return arrayofmovies;

}
    public static void two(final ArrayList<movies>movies, ArrayList<movies> parce, ArrayAdapter<movies> arrayAdapter, final AdapterView<ListAdapter> viewGroup, ProgressBar progressBar, final Activity activity, final Context context, final comunicator comunicator) {


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

                utility.go_to_movie_detail(array,activity,context,comunicator);


            } });


    }


    }







