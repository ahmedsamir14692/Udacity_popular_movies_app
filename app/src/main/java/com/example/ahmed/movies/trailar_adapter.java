package com.example.ahmed.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 8/2/16.
 */
public class trailar_adapter extends ArrayAdapter<trailaar> {

    Context context;
    int resource;
    ArrayList<trailaar> objects;
    public trailar_adapter(Context context, int resource, ArrayList<trailaar> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(resource,parent,false);
        TextView textView= (TextView) view.findViewById(R.id.text);
        textView.setText(objects.get(position).getName());
        return view;
    }

}
