package com.example.ahmed.movies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ahmed on 7/31/16.
 */
public class movies_adpater extends ArrayAdapter<movies> {
    Context context;
    int resource;
    ArrayList<movies> objects;
    GridView viewGroup;
    Activity activity;
    Integer xx;

    public movies_adpater(Context context, int resource, ArrayList<movies> objects, GridView viewGroup, Activity activity) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        this.viewGroup=viewGroup;
        this.activity=activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(resource,parent,false);
        ImageView imageView= (ImageView) view.findViewById(R.id.mainimage);
        xx= utility.fix(imageView,viewGroup,context);
        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w"+xx.toString()+"/"+objects.get(position).getImage_of_poster(), imageView);
        return view;
    }
}
