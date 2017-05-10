package witkowska.app2;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-04-11.
 */

public class Movie implements Serializable {

    private String title, genre, year;
    int pic_resource, desc_resource, eye_resource;
    float rating;
    boolean has_been_seen;

    public Movie() {}

    public Movie (String title, String genre, String year, int pic_resource, int desc_resource) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.pic_resource = pic_resource;
        this.desc_resource = desc_resource;
        rating = 0;
        //eye_resource = R.drawable.empty;
        has_been_seen = true;
    }

    public String getTitle() { return title; }
    public void setTitle(String name) { this.title = name; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getPictureResource() { return pic_resource; }
    public void setPictureResource (int pic_resource) { this.pic_resource = pic_resource; }

    public int getDescriptionResource() { return desc_resource; }
    public void setDescriptionResource (int desc_resource) { this.desc_resource = desc_resource; }

    public float getRating() { return rating; }
    public void setRating (float rating) { this.rating = rating; }

//    public int getEyeResource() { return eye_resource; }
//    public void setEyeResource (int eye_resource) { this.eye_resource = eye_resource; }

    public Boolean getHasBeenSeen() { return has_been_seen; }
    public void setHasBeenSeen(Boolean has_been_seen) { this.has_been_seen = has_been_seen; }


}
