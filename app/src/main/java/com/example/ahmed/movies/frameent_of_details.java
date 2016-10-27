package com.example.ahmed.movies;

import android.app.Fragment;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by ahmed on 7/31/16.
 */
public class frameent_of_details extends Fragment {
ListView listView;
     trailar_adapter trailar_adapter;
    ScrollView scrollView;

TextView textView;
    TextView name;
    ImageView poster;
TextView relase_date;

    RatingBar rate;
    Button fav;


    TextView overview;
   static Bundle bundle;
    String[] array;
String id;
    String[] saconnd;
    SharedPreferences sharedPreferences;
String poterimage;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
           saconnd=savedInstanceState.getStringArray("ahmed");
            FrameLayout frameLayout= (FrameLayout) getActivity().findViewById(R.id.frame);
            if(frameLayout !=null)
            {
                if(bundle==null)
                frameLayout.setVisibility(View.VISIBLE);
            }
        }

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config);

        Bundle b=getActivity().getIntent().getExtras();
        if(b==null)
            b=bundle;

        trailar_adapter=new trailar_adapter(getActivity(),R.layout.one_trailar_item,new ArrayList<trailaar>());

        if(b!=null) {
            array = b.getStringArray("infodetails");

            if (array != null) {
               if(saconnd!=null)
                   array=saconnd;
                id = array[5];
                poterimage = array[0];
            }
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.rgmentofdetails,container,false);


        if(array!=null) {
            name = (TextView) view.findViewById(R.id.name_of_movee);
            poster = (ImageView) view.findViewById(R.id.poster);
            relase_date = (TextView) view.findViewById(R.id.year);
            overview = (TextView) view.findViewById(R.id.overview);
            listView = (ListView) view.findViewById(R.id.listview);
            listView.setFocusable(false);
            scrollView= (ScrollView) view.findViewById(R.id.scrool);
            scrollView.fullScroll(ScrollView.FOCUS_UP);
           scrollView.smoothScrollTo(0,0);
            textView = (TextView) view.findViewById(R.id.re);
            if (array != null) {
                new Trilarasyn(trailar_adapter, listView, getActivity()).execute("http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=021e100edb71840d6712a77681cd4ed3");
                new reviewasyn(textView, getActivity()).execute("http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=021e100edb71840d6712a77681cd4ed3");
            }
            rate = (RatingBar) view.findViewById(R.id.bar);

            LayerDrawable stars = (LayerDrawable) rate.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            name.setText(array[1]);
            ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w185/" + array[0], poster);
            overview.setText(array[2]);
            rate.setRating(Float.parseFloat(array[4]) / 2);
            relase_date.setText("release date: " + array[3]);
            fav = (Button) view.findViewById(R.id.fav);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    sharedPreferences = getActivity().getSharedPreferences("favourt", Context.MODE_PRIVATE);

                   String momiev = sharedPreferences.getString("array of fav", "");

                  if(!momiev.contains(poterimage)) {
                      editor = sharedPreferences.edit();
                      StringBuilder sbb = new StringBuilder(momiev);
                      sbb.append(poterimage).append("~");
                      editor.putString("array of fav", sbb.toString()).apply();
                      String[] momieev = sharedPreferences.getString("array of fav", "").split("~");
                      SharedPreferences sharedPreferencest = getActivity().getSharedPreferences(new Integer((momieev.length) - 1).toString(), Context.MODE_PRIVATE);
                      SharedPreferences.Editor ed = sharedPreferencest.edit();
                      StringBuilder sb = new StringBuilder();
                      for (int i = 0; i < array.length; i++) {
                          sb.append(array[i]).append("~");
                      }
                      ed.putString("infodetails", sb.toString()).apply();
                      Toast toast=Toast.makeText(getActivity(),"the movie is added to your favourites",Toast.LENGTH_LONG);
                      toast.show();
                  }
                    else
                  {
                      Toast toast=Toast.makeText(getActivity(),"the movie is already in your favourites",Toast.LENGTH_LONG);
                      toast.show();

                  }

                }
            });


        }

return view;
    }
public void changebndle( Bundle undle)
{

    bundle=undle;
}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bundle!=null)
            outState.putStringArray("ahmed",bundle.getStringArray("infodetails"));
    }
}
