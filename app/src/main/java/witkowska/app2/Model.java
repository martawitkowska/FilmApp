package witkowska.app2;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-04-14.
 */

public class Model implements Serializable {

    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;

    public int type;
    Movie movie;

    public Model (int type, Movie movie){
        this.type = type;
        this.movie = movie;
    }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

}
