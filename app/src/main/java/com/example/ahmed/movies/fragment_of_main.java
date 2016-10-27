package com.example.ahmed.movies;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ahmed on 7/31/16.
 */
public class fragment_of_main extends Fragment {
    GridView gridView;
    movies_adpater movies_adpater;
    myAsyntask myAsyntask;
    ArrayList<movies> parce;

    adpter_of_image adpter_of_image;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config);
        Log.d("cycle","          Fragment               ddeattash");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  View root_of_fargment=inflater.inflate(R.layout.layout_of_fragmentmain,container,false);
        gridView= (GridView) root_of_fargment.findViewById(R.id.gridlist);

progressBar= (ProgressBar) root_of_fargment.findViewById(R.id.progress);
        movies_adpater=new movies_adpater(getActivity(),R.layout
                .image_of_main,new ArrayList<movies>(),gridView,getActivity());
        if(savedInstanceState !=null)
        { if(utility.marker==7|| utility.marker==5){
        {

            parce=savedInstanceState.getParcelableArrayList("save");
           movies_adpater=new movies_adpater(getActivity(),R.layout
                   .image_of_main,parce,gridView,getActivity());
gridView.setAdapter(movies_adpater);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String[] array={parce.get(i).getImage_of_poster(),parce.get(i).getOrgilal_title(),

                            parce.get(i).getOver_view(),parce.get(i).getRelase_date(),new Double(parce.get(i).getRate()).toString(),
                            new Integer(parce.get(i).getId()).toString(),

                    };
                    utility.go_to_movie_detail(array,getActivity(),getActivity(), (comunicator) getActivity());


                } });

        }}
        if(utility.marker==2)
        {

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(new Integer(i).toString(), Context.MODE_PRIVATE);
                    String y=  sharedPreferences.getString("infodetails","");

                    String[] s = y.split("~");

                    utility.go_to_movie_detail(s,getActivity(),getActivity(), (comunicator) getActivity());
                }
            });

            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("favourt", Context.MODE_PRIVATE);
            List<String> list= Arrays.asList(sharedPreferences.getString("array of fav","").split("~"));
            adpter_of_image=new adpter_of_image(getActivity(),R.layout.image_of_main,list,gridView);
            gridView.setAdapter(adpter_of_image);

        }}

        return root_of_fargment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isOnline()&& utility.marker==0)
        {


            myAsyntask=new myAsyntask(0,movies_adpater,gridView,getActivity(),getActivity(),progressBar);
            myAsyntask.execute("http://api.themoviedb.org/3/movie/popular?api_key=021e100edb71840d6712a77681cd4ed3");
            utility.marker=7;
        }
        else
        {
            if(utility.marker==0) {

                Toast toast = Toast.makeText(getActivity(), "there is no internet connection plz check you connection and if you can see any data this may be an old data", Toast.LENGTH_LONG);
                toast.show();
                utility.marker=7;
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("refresh",Context.MODE_PRIVATE);

                String jason =sharedPreferences.getString("data",null);
                if(jason!=null)
                {
                    utility.two(utility.take_string_give_array_of_movies(jason),parce,movies_adpater,gridView,progressBar,getActivity(),getActivity(), (comunicator) getActivity());

                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
myAsyntask myAsyntask=new myAsyntask(0,movies_adpater,gridView,getActivity(),getActivity(),progressBar);
        if(item.getItemId()==R.id.refresh)
        {
            Log.d("hhh","hhhhhhhhhhhhhhh option item");
            utility.marker=7;
        myAsyntask.execute("http://api.themoviedb.org/3/movie/popular?api_key=021e100edb71840d6712a77681cd4ed3");
            Log.d("hhh","hhhhhhhhhhhhhhh after asyn");
        }
        if(item.getItemId()==R.id.toprated)
        { utility.marker=5;
            myAsyntask.execute("http://api.themoviedb.org/3/movie/top_rated?api_key=021e100edb71840d6712a77681cd4ed3");

        }
        if(item.getItemId()==R.id.fav)
        {
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("favourt", Context.MODE_PRIVATE);
            String array=sharedPreferences.getString("array of fav","");
            if(array.length()==0){
                Toast toast=Toast.makeText(getActivity(),"you have not added any movies to your favourites list yet",Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
            utility.marker=2;
          // Intent intent=new Intent(getActivity(),Main2Activity.class);
          //  getActivity().startActivity(intent);
gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       SharedPreferences sharedPreferences = getActivity().getSharedPreferences(new Integer(i).toString(), Context.MODE_PRIVATE);
       String y=  sharedPreferences.getString("infodetails","");

        String[] s = y.split("~");

        utility.go_to_movie_detail(s,getActivity(),getActivity(), (comunicator) getActivity());
    }
});


            List<String> list= Arrays.asList(sharedPreferences.getString("array of fav","").split("~"));
            adpter_of_image=new adpter_of_image(getActivity(),R.layout.image_of_main,list,gridView);
            gridView.setAdapter(adpter_of_image);


        }}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        {
            if(utility.marker==7|| utility.marker==5)
outState.putParcelableArrayList("save", com.example.ahmed.movies.myAsyntask.parce);
            if(utility.marker==2)
                outState.putInt("fav",2);

        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("cycle","          Fragment               ddeattash");
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
