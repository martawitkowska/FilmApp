package witkowska.app2;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import layout.DescriptionFragmentOne;

public class MainActivity extends AppCompatActivity {

    private List<Model> movieList = new ArrayList<>();
    private ArrayList<Boolean> moviesToSee = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(movieList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null)
            savedInstanceState (savedInstanceState);
        else
            prepareMovieData();

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,
                new RecyclerTouchListener.MyClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Movie movie = movieList.get(position).getMovie();
                        //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                        Intent descriptionIntent = new Intent(getApplicationContext(), DescriptionActivity.class);

                        Bundle movie_data = new Bundle();
                        movie_data.putInt("Position", position);
                        movie_data.putSerializable("Movies", (Serializable)movieList);

                        descriptionIntent.putExtra("Bundle", movie_data);
                        startActivityForResult(descriptionIntent, 123);

//                        DescriptionFragmentOne fragment = new DescriptionFragmentOne();
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.fragmentMain, fragment);
//                        transaction.commit();

                    }
                    @Override
                    public void onLongClick(View view, int position) {
                        Movie movie = movieList.get(position).getMovie();
                        if (movie.getHasBeenSeen()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.to_see_false_1) + movie.getTitle() + getString(R.string.to_see_false_2), Toast.LENGTH_LONG).show();
                            movie.setHasBeenSeen(false);
                            moviesToSee.set(position, true);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), getString(R.string.to_see_true), Toast.LENGTH_SHORT).show();
                            movie.setHasBeenSeen(true);
                            moviesToSee.set(position, false);
                        }
                        mAdapter.notifyItemChanged(position);
                    }
                }
        ));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = viewHolder.getAdapterPosition();
//                    // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getLayoutPosition();
                moviesToSee.remove(position);
                movieList.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Movie has been successfully removed", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

//    public void onResume() {
//
//        super.onResume();
//    }


    public void savedInstanceState(Bundle savedInstanceState) {
        moviesToSee = (ArrayList<Boolean>) savedInstanceState.getSerializable("moviesToSee");
//        movieList = (ArrayList<Model>) savedInstanceState.getSerializable("movieList");
        ArrayList <Model> temp = (ArrayList<Model>) savedInstanceState.getSerializable("movieList");
        for (int i=0; i<temp.size(); i++) {
            Movie movie = temp.get(i).getMovie();
            if (i % 2 == 0) {
                movieList.add(new Model(Model.LEFT_SIDE, new Movie(movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPictureResource(), movie.getDescriptionResource(), movie.getRating())));
//                movieList.get(i).getMovie().setRating(movie.getRating());
            }
            else {
                movieList.add(new Model(Model.RIGHT_SIDE, new Movie(movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPictureResource(), movie.getDescriptionResource(), movie.getRating())));
//                movieList.get(i).getMovie().setRating(movie.getRating());
            }
            mAdapter.notifyItemChanged(i);
        }
        //Toast.makeText(getApplicationContext(), "movieList.size() = " + String.valueOf(movieList.size()) + "\nmoviesToSee = " + String.valueOf(moviesToSee.size()), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("movieList", (Serializable)movieList);
        outState.putSerializable("moviesToSee", moviesToSee);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult (int reqId, int resC, Intent intent) {
        if (resC == Activity.RESULT_OK && reqId == 123) {
            Bundle movie_data = intent.getExtras();
            int position = movie_data.getInt("Position");
            Movie movie = movieList.get(position).getMovie();
            //movie.setTitle(movie_data.getString("Title"));

            //WORKING
            float rating = movie_data.getFloat("RatingFromFragment");
            movie.setRating(rating);
//            Toast.makeText(getApplicationContext(), "Rating = " + String.valueOf(rating), Toast.LENGTH_SHORT).show();

            for (int i=0; i<moviesToSee.size(); i++)
                if (moviesToSee.get(i))
                    movieList.get(i).getMovie().setHasBeenSeen(false);
            mAdapter.notifyItemChanged(position);

//            movieList.get(position).setMovie(movie);

//            Toast.makeText(getApplicationContext(), "Rating: " + String.valueOf(rating) + "\nPosition: " + String.valueOf(position), Toast.LENGTH_LONG).show();
        }
    }


//   public void ChangeFragment(View view) {
//        Fragment fragment;
//    }


    private void prepareMovieData() {
        Movie movie = new Movie(getString(R.string.title_walle), getString(R.string.genre_an_sf), 2008, R.drawable.walle, R.string.des_walle);
//        Integer[] pictures_and_actors_walle = {R.drawable.narnia01, R.drawable.narnia02, R.drawable.narnia03, R.drawable.narnia04, R.drawable.narnia05, R.drawable.narnia06,
//                R.drawable.narnia_1, R.drawable.narnia_2, R.drawable.narnia_3, R.string.narnia_actor1_name, R.string.narnia_actor2_name, R.string.narnia_actor3_name};
//        movie.setPicturesActors(pictures_and_actors_walle);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_csl), getString(R.string.genre_co_dr_ro), 2011, R.drawable.crazy_stupid_love, R.string.des_csl);
        Integer[] pictures_and_actors_csl = {R.drawable.csl_01, R.drawable.csl_02,R.drawable.csl_03,R.drawable.csl_04,R.drawable.csl_05,R.drawable.csl_06,
                R.drawable.csl_1,R.drawable.csl_2,R.drawable.csl_3, R.string.csl_1, R.string.csl_2, R.string.csl_3};
        movie.setPicturesActors(pictures_and_actors_csl);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_big_hero_6), getString(R.string.genre_an_ad_co), 2014, R.drawable.big_hero_6, R.string.des_big_hero_6);
        Integer[] pictures_and_actors_baymax = {R.drawable.big_hero_01, R.drawable.big_hero_02, R.drawable.big_hero_03, R.drawable.big_hero_04, R.drawable.big_hero_05, R.drawable.big_hero_06,
                R.drawable.big_hero_1, R.drawable.big_hero_2, R.drawable.big_hero_3, R.string.big_hero_1, R.string.big_hero_2, R.string.big_hero_3};
        movie.setPicturesActors(pictures_and_actors_baymax);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_moana), getString(R.string.genre_an_ad), 2016, R.drawable.moana, R.string.des_moana);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_hercules), getString(R.string.genre_an_ad), 1997, R.drawable.hercules, R.string.des_hercules);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_el_dorado), getString(R.string.genre_an_ad), 2000, R.drawable.el_dorado, R.string.general_description);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_godfather), getString(R.string.genre_dr_ga), 1972, R.drawable.godfather, R.string.general_description);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_zoo), getString(R.string.genre_an_ad_co), 2016, R.drawable.zootopia, R.string.des_zootopia);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_hoc), getString(R.string.genre_dr_po), 2013, R.drawable.house_of_cards, R.string.general_description);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_narnia), getString(R.string.genre_ad_fa), 2005, R.drawable.narnia, R.string.general_description);
        Integer[] pictures_and_actors_narnia = {R.drawable.narnia01, R.drawable.narnia02, R.drawable.narnia03, R.drawable.narnia04, R.drawable.narnia05, R.drawable.narnia06,
                R.drawable.narnia_1, R.drawable.narnia_2, R.drawable.narnia_3, R.string.narnia_1, R.string.narnia_2, R.string.narnia_3};
        movie.setPicturesActors(pictures_and_actors_narnia);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_easy_a), getString(R.string.genre_co), 2010, R.drawable.easy_a, R.string.general_description);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_lion_king), getString(R.string.genre_an), 1994, R.drawable.lion_king, R.string.general_description);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_up), getString(R.string.genre_an_ad_co), 2009, R.drawable.up, R.string.general_description);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_inside_out), getString(R.string.genre_an_co), 2015, R.drawable.inside_out, R.string.general_description);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_asterix), getString(R.string.genre_ad_co), 2002, R.drawable.asterix_obelix, R.string.general_description);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_httyd), getString(R.string.genre_ad_co), 2002, R.drawable.dragon, R.string.general_description);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_legally_blonde), getString(R.string.genre_ad_co), 2002, R.drawable.legally_blond, R.string.general_description);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        for (int i=0; i<movieList.size();i++)
            moviesToSee.add(false);

        mAdapter.notifyDataSetChanged();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Toast.makeText(getApplicationContext(), "You clicked settings :)", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
