package com.example.ahmed.movies;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by ahmed on 8/4/16.
 */
public class fragment_of_fav extends Fragment{
    GridView gridView;
    adpter_of_image adpter_of_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_of_fav,container,false);
        gridView= (GridView) view.findViewById(R.id.gridoffav);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("favourt", Context.MODE_PRIVATE);
        List<String> list= Arrays.asList(sharedPreferences.getString("array of fav","").split("~"));
adpter_of_image=new adpter_of_image(getActivity(),R.layout.image_of_main,list,gridView);
        gridView.setAdapter(adpter_of_image);
        return  view;
    }


}
