package com.example.ahmed.movies;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 8/4/16.
 */
public class adpter_of_image extends ArrayAdapter {
    Context context;
    int resource;
    List<review> objects;
    GridView gridView;
    Integer xx;

    public adpter_of_image(Context context, int resource, List objects,GridView gridView) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        this.gridView=gridView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(resource,parent,false);
        ImageView imageView= (ImageView) view.findViewById(R.id.mainimage);
        xx=utility.fix(imageView,gridView,context);
        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w"+xx.toString()+"/"+objects.get(position),imageView);
        return view;}

}
