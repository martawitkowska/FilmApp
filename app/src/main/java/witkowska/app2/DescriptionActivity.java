package witkowska.app2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-04-13.
 */

public class DescriptionActivity extends AppCompatActivity {

    private List<Model> movieList = new ArrayList<>();
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);

        //mAdapter = new MoviesAdapter(movieList);
        Intent intent = getIntent();
        final Bundle movie_data = intent.getBundleExtra("Bundle");
        movieList = (ArrayList<Model>) movie_data.getSerializable("Movies");
        final int position = movie_data.getInt("Position");

        Movie movie = movieList.get(position).getMovie();

        TextView title_tv = (TextView) findViewById(R.id.title);
        TextView genre_tv = (TextView) findViewById(R.id.genre);
        TextView year_tv = (TextView) findViewById(R.id.year);
        final TextView description_tv = (TextView) findViewById(R.id.description);
        final RatingBar rating_bar = (RatingBar) findViewById(R.id.rating_bar);
        ImageView picture = (ImageView) findViewById(R.id.picture);

        title_tv.setText(movie.getTitle());
        genre_tv.setText(movie.getGenre());
        year_tv.setText(movie.getYear());
        description_tv.setText(getString(movie.getDescriptionResource()));
        picture.setImageResource(movie.getPictureResource());

        //float rate = movie.getRating();
        //description_tv.setText(String.valueOf(movie.getRating()));

        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                description_tv.setText(String.valueOf(rating));
                //doesn't work - rating = 0 in MainActivity
                movie_data.putFloat("Rating", rating);
            }
        });

//        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                rating_bar.setRating(rating);
//            }
//        });

        //Toast.makeText(getApplicationContext(), "Counter = " + counter, Toast.LENGTH_LONG).show();

        //nothing changes
        movie.setTitle("Blablabla");

        //movie_data.putString("Title", "Blaaaaaaaaa");
        //movie_data.putFloat("Rating", rating_bar.getRating());
        intent.putExtras(movie_data);
        setResult(Activity.RESULT_OK, intent);

//        if (savedInstanceState != null) {
//            Toast.makeText(getApplicationContext(), "savedInstantState works\n" + savedInstanceState.getString("Test"), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}

