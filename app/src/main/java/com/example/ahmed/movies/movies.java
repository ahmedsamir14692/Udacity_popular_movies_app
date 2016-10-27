package com.example.ahmed.movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmed on 7/31/16.
 */
public class movies implements Parcelable {

    private String orgilal_title;
    private String relase_date ;
    private double rate ;
    private String image_of_poster ;
    private String over_view;
    private int id;
    public movies(){


    }

    public String getOrgilal_title() {
        return orgilal_title;
    }

    public void setOrgilal_title(String orgilal_title) {
        this.orgilal_title = orgilal_title;
    }

    public String getRelase_date() {
        return relase_date;
    }

    public void setRelase_date(String relase_date) {
        this.relase_date = relase_date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getImage_of_poster() {
        return image_of_poster;
    }

    public void setImage_of_poster(String image_of_poster) {
        this.image_of_poster = image_of_poster;
    }

    public String getOver_view() {
        return over_view;
    }

    public void setOver_view(String over_view) {
        this.over_view = over_view;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected movies(Parcel in) {
        orgilal_title = in.readString();
        relase_date = in.readString();
        rate = in.readDouble();
        image_of_poster = in.readString();
        over_view = in.readString();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orgilal_title);
        dest.writeString(relase_date);
        dest.writeDouble(rate);
        dest.writeString(image_of_poster);
        dest.writeString(over_view);
        dest.writeInt(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<movies> CREATOR = new Parcelable.Creator<movies>() {
        @Override
        public movies createFromParcel(Parcel in) {
            return new movies(in);
        }

        @Override
        public movies[] newArray(int size) {
            return new movies[size];
        }
    };
}