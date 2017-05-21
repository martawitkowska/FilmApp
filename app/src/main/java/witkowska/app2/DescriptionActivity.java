package witkowska.app2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import layout.ActorsFragmentThree;
import layout.DescriptionFragmentOne;
import layout.PhotosFragmentTwo;

/**
 * Created by Administrator on 2017-04-13.
 */


public class DescriptionActivity extends AppCompatActivity {

    private List<Model> movieList = new ArrayList<>();
    Bundle movie_data;
    DescriptionFragmentOne fragment_one;
    int position;
    boolean fragment_two;
    float saved_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);
//        ButterKnife.bind(this);


        //mAdapter = new MoviesAdapter(movieList);
        Intent intent = getIntent();
        movie_data = intent.getBundleExtra("Bundle");
        movieList = (ArrayList<Model>) movie_data.getSerializable("Movies");
        position = movie_data.getInt("Position");

        Movie movie = movieList.get(position).getMovie();
        saved_rating = movie.getRating();

        startFragmentOne();



//        TextView title_tv = (TextView) findViewById(R.id.title);
//        TextView genre_tv = (TextView) findViewById(R.id.genre);
//        TextView year_tv = (TextView) findViewById(R.id.year);
//        final TextView description_tv = (TextView) findViewById(R.id.description);
//        final RatingBar rating_bar = (RatingBar) findViewById(R.id.rating_bar);
//        ImageView picture = (ImageView) findViewById(R.id.picture);
//
//        title_tv.setText(movie.getTitle());
//        genre_tv.setText(movie.getGenre());
//        year_tv.setText(movie.getYear());
//        description_tv.setText(getString(movie.getDescriptionResource()));
//        picture.setImageResource(movie.getPictureResource());
//
//        rating_bar.setRating(loadRating());
//
//        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                //description_tv.setText(String.valueOf(rating));
//                saveRating(rating);
//            }
//        });
//
//        Toast.makeText(getApplicationContext(), "onCreate() in DesActivity", Toast.LENGTH_SHORT).show();


//        Button button = (Button)findViewById(R.id.button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "BLAAAAA", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });


//        if (fragment_one != null) {
//            intent.putExtra("Rating", fragment_one.getRating());
//            Toast.makeText(getApplicationContext(), "Rating = '" + String.valueOf(movie_data.getFloat("Rating",0)), Toast.LENGTH_LONG).show();
//        }

//        float rating = 0;
//        if (savedInstanceState != null) {
//            rating = savedInstanceState.getFloat("FragmentRating");
//            //Toast.makeText(getApplicationContext(), "Visiting DescriptionActivity()", Toast.LENGTH_SHORT).show();
//        }
//
//
//
//        movie_data.putFloat("Rating", rating);
//        intent.putExtras(movie_data);
//        setResult(Activity.RESULT_OK, intent);

    }


    public void startFragmentOne() {
//        movieList.get(position).getMovie().setRating(fragment_one.getRating());
//        movie_data.putSerializable("Movies", (Serializable)movieList);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fragment_two = false;
        movie_data.putFloat("SavedRating", saved_rating);

        fragment_one = new DescriptionFragmentOne();
        fragment_one.setArguments(movie_data);

        ft.replace(R.id.fragment_container, fragment_one);
        ft.commit();
    }

    public void pictureClick(View view) {
//        Toast.makeText(getApplicationContext(), "Rating = " + String.valueOf(fragment_one.getRating()), Toast.LENGTH_SHORT).show();
        fragment_two = true;
        saved_rating = fragment_one.getRating();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        PhotosFragmentTwo fragment_two = new PhotosFragmentTwo();
        fragment_two.setArguments(movie_data);
        ActorsFragmentThree fragment_three = new ActorsFragmentThree();
        fragment_three.setArguments(movie_data);

        ft.replace(R.id.fragment_container, fragment_two);
        ft.add(R.id.fragment_container2, fragment_three);
        ft.commit();
    }

//    public void onPause() {
//        //Toast.makeText(getApplicationContext(), "Visiting DescriptionActivity()", Toast.LENGTH_SHORT).show();
//        super.onPause();
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean("Fragment", fragment_two);
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        fragment_two = savedInstanceState.getBoolean("Fragment");
////        Toast.makeText(getApplicationContext(), "Fragment = " + String.valueOf(fragment_two) + "\nfrom onRestore() in DesActivity", Toast.LENGTH_SHORT).show();
//    }


    @Override
    public void onBackPressed() {
        Intent intent_out = new Intent();

//        Toast.makeText(getApplicationContext(), "DesActivity - onBackPressed()\nFragment = " + String.valueOf(fragment_two), Toast.LENGTH_SHORT).show();

        if (fragment_two) {
            startFragmentOne();
        }
        else {
            if (fragment_one != null)
                intent_out.putExtra("RatingFromFragment", fragment_one.getRating());
            intent_out.putExtra("Position", position);
            setResult(RESULT_OK, intent_out);
            finish();
        }

//        intent_out.putExtra("MovieList", (Serializable)movieList);
//        Toast.makeText(getApplicationContext(), "onBackPressed()", Toast.LENGTH_SHORT).show();

    }



//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if(fragment_one!=null){
//            outState.putFloat("Rating",fragment_one.getRating());
//        }
////        else if(savedRating!=0) {
////            outState.putFloat(rating, savedRating);
////        }
//    }



//    public void ButtonClick(View view) {

//        startActivity(new Intent(DescriptionActivity.this, MainActivity.class));

//        DescriptionFragmentOne fragment = new DescriptionFragmentOne();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.commit();

//    }

//    public void saveRating(float r){
//        SharedPreferences sharedPreferences = getSharedPreferences("folder",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putFloat("rating",r);
//        editor.commit();
//    }
//
//    public float loadRating(){
//        SharedPreferences sharedPreferences = getSharedPreferences("folder",MODE_PRIVATE);
//        float r = sharedPreferences.getFloat("rating",0f);
//        return r;
//    }


}

