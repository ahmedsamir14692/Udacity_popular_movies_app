package com.example.ahmed.movies;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity implements comunicator{
frameent_of_details frameent_of_details;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

if(findViewById(R.id.frame)!=null)
        {
            frameLayout= (FrameLayout) findViewById(R.id.frame);
            frameent_of_details=new frameent_of_details();
            getFragmentManager().beginTransaction().add(R.id.frame,frameent_of_details).commit();
            if(savedInstanceState!=null&&frameent_of_details.bundle!=null)
            {

                frameLayout.setVisibility(View.VISIBLE);

            }
        }

        Log.d("cycle","          creat");
    }



    @Override
    public void changebundle(Bundle bundle) {
        frameent_of_details.changebndle(bundle);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("cycle","          save");
        if(frameLayout!=null)
        {

            outState.putString(" "," ");
        }
        Log.d("cycle","          save");
    }

    @Override
    public void finish() {
        super.finish();
        frameent_of_details=new frameent_of_details();
        if(findViewById(R.id.frame)!=null&&frameent_of_details.bundle!=null)
            com.example.ahmed.movies.frameent_of_details.bundle=null;
        Log.d("cycle","          finish");
    }



}
