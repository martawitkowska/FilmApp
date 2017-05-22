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
                movieList.add(new Model(Model.LEFT_SIDE, movie.clone()));

//                movieList.add(new Model(Model.LEFT_SIDE, new Movie(movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPictureResource(), movie.getDescriptionResource(), movie.getRating())));
//                movieList.get(i).getMovie().setRating(movie.getRating());
            }
            else {
                movieList.add(new Model(Model.RIGHT_SIDE, movie.clone()));
//                movieList.add(new Model(Model.RIGHT_SIDE, new Movie(movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPictureResource(), movie.getDescriptionResource(), movie.getRating())));
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
        Integer[] pictures_and_actors_walle = {R.drawable.walle_01, R.drawable.walle_02, R.drawable.walle_03, R.drawable.walle_04, R.drawable.walle_05, R.drawable.walle_06,
                R.drawable.walle_1, R.drawable.walle_2, R.drawable.walle_3, R.string.walle_1, R.string.walle_2, R.string.walle_3};
        movie.setPicturesActors(pictures_and_actors_walle);
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
        Integer[] pictures_and_actors_moana = {R.drawable.moana_01, R.drawable.moana_02, R.drawable.moana_03, R.drawable.moana_04, R.drawable.moana_05, R.drawable.moana_06,
                R.drawable.moana_1, R.drawable.moana_2, R.drawable.moana_3, R.string.moana_1, R.string.moana_2, R.string.moana_3};
        movie.setPicturesActors(pictures_and_actors_moana);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_hercules), getString(R.string.genre_an_ad), 1997, R.drawable.hercules, R.string.des_hercules);
        Integer[] pictures_and_actors_hercules = {R.drawable.hercules_01, R.drawable.hercules_02, R.drawable.hercules_03, R.drawable.hercules_04, R.drawable.hercules_05, R.drawable.hercules_06,
                R.drawable.hercules_1, R.drawable.hercules_2, R.drawable.hercules_3, R.string.hercules_1, R.string.hercules_2, R.string.hercules_3};
        movie.setPicturesActors(pictures_and_actors_hercules);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_el_dorado), getString(R.string.genre_an_ad), 2000, R.drawable.el_dorado, R.string.des_el_dorado);
        Integer[] pictures_and_actors_el_dorado = {R.drawable.el_dorado_01, R.drawable.el_dorado_02, R.drawable.el_dorado_03, R.drawable.el_dorado_04, R.drawable.el_dorado_05, R.drawable.el_dorado_06,
                R.drawable.el_dorado_1, R.drawable.el_dorado_2, R.drawable.el_dorado_3, R.string.el_dorado_1, R.string.el_dorado_2, R.string.el_dorado_3};
        movie.setPicturesActors(pictures_and_actors_el_dorado);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_godfather), getString(R.string.genre_dr_ga), 1972, R.drawable.godfather, R.string.des_godfather);
        Integer[] pictures_and_actors_gf = {R.drawable.gf_01, R.drawable.gf_02, R.drawable.gf_03, R.drawable.gf_04, R.drawable.gf_05, R.drawable.gf_06,
                R.drawable.gf_1, R.drawable.gf_2, R.drawable.gf_3, R.string.gf_1, R.string.gf_2, R.string.gf_3};
        movie.setPicturesActors(pictures_and_actors_gf);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_zoo), getString(R.string.genre_an_ad_co), 2016, R.drawable.zootopia, R.string.des_zootopia);
        Integer[] pictures_and_actors_zoo = {R.drawable.zoo_01, R.drawable.zoo_02, R.drawable.zoo_03, R.drawable.zoo_04, R.drawable.zoo_05, R.drawable.zoo_06,
                R.drawable.zoo_1, R.drawable.zoo_2, R.drawable.zoo_3, R.string.zoo_1, R.string.zoo_2, R.string.zoo_3};
        movie.setPicturesActors(pictures_and_actors_zoo);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_hoc), getString(R.string.genre_dr_po), 2013, R.drawable.house_of_cards, R.string.des_hoc);
        Integer[] pictures_and_actors_hoc = {R.drawable.hoc_01, R.drawable.hoc_02, R.drawable.hoc_03, R.drawable.hoc_04, R.drawable.hoc_05, R.drawable.hoc_06,
                R.drawable.hoc_1, R.drawable.hoc_2, R.drawable.hoc_3, R.string.hoc_1, R.string.hoc_2, R.string.hoc_3};
        movie.setPicturesActors(pictures_and_actors_hoc);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_narnia), getString(R.string.genre_ad_fa), 2005, R.drawable.narnia, R.string.des_narnia);
        Integer[] pictures_and_actors_narnia = {R.drawable.narnia01, R.drawable.narnia02, R.drawable.narnia03, R.drawable.narnia04, R.drawable.narnia05, R.drawable.narnia06,
                R.drawable.narnia_1, R.drawable.narnia_2, R.drawable.narnia_3, R.string.narnia_1, R.string.narnia_2, R.string.narnia_3};
        movie.setPicturesActors(pictures_and_actors_narnia);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_easy_a), getString(R.string.genre_co), 2010, R.drawable.easy_a, R.string.des_easy);
        Integer[] pictures_and_actors_easy = {R.drawable.easy_01, R.drawable.easy_02, R.drawable.easy_03, R.drawable.easy_04, R.drawable.easy_05, R.drawable.easy_06,
                R.drawable.easy_1, R.drawable.easy_2, R.drawable.easy_3, R.string.easy_1, R.string.easy_2, R.string.easy_3};
        movie.setPicturesActors(pictures_and_actors_easy);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_lion_king), getString(R.string.genre_an), 1994, R.drawable.lion_king, R.string.des_lion);
        Integer[] pictures_and_actors_lion = {R.drawable.lion_01, R.drawable.lion_02, R.drawable.lion_03, R.drawable.lion_04, R.drawable.lion_05, R.drawable.lion_06,
                R.drawable.lion_1, R.drawable.lion_2, R.drawable.lion_3, R.string.lion_1, R.string.lion_2, R.string.lion_3};
        movie.setPicturesActors(pictures_and_actors_lion);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_up), getString(R.string.genre_an_ad_co), 2009, R.drawable.up, R.string.des_up);
        Integer[] pictures_and_actors_up = {R.drawable.up_01, R.drawable.up_02, R.drawable.up_03, R.drawable.up_04, R.drawable.up_05, R.drawable.up_06,
                R.drawable.up_1, R.drawable.up_2, R.drawable.up_3, R.string.up_1, R.string.up_2, R.string.up_3};
        movie.setPicturesActors(pictures_and_actors_up);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_inside_out), getString(R.string.genre_an_co), 2015, R.drawable.inside_out, R.string.des_inside_out);
        Integer[] pictures_and_actors_io = {R.drawable.io_01, R.drawable.io_02, R.drawable.io_03, R.drawable.io_04, R.drawable.io_05, R.drawable.io_06,
                R.drawable.io_1, R.drawable.io_2, R.drawable.io_3, R.string.io_1, R.string.io_2, R.string.io_3};
        movie.setPicturesActors(pictures_and_actors_io);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_asterix), getString(R.string.genre_ad_co), 2002, R.drawable.asterix_obelix, R.string.des_asterix_obelix);
        Integer[] pictures_and_actors_as_ob = {R.drawable.as_ob_01, R.drawable.as_ob_02, R.drawable.as_ob_03, R.drawable.as_ob_04, R.drawable.as_ob_05, R.drawable.as_ob_06,
                R.drawable.as_ob_1, R.drawable.as_ob_2, R.drawable.as_ob_3, R.string.as_ob_1, R.string.as_ob_2, R.string.as_ob_3};
        movie.setPicturesActors(pictures_and_actors_as_ob);
        movieList.add(new Model(Model.LEFT_SIDE, movie));

        movie = new Movie(getString(R.string.title_httyd), getString(R.string.genre_an_ad_fa), 2010, R.drawable.dragon, R.string.des_hhtyd);
        Integer[] pictures_and_actors_httyd = {R.drawable.httyd_01, R.drawable.httyd_04, R.drawable.httyd_06, R.drawable.httyd_02, R.drawable.httyd_05, R.drawable.httyd_03,
                R.drawable.httyd_1, R.drawable.httyd_2, R.drawable.httyd_3, R.string.httyd_1, R.string.httyd_2, R.string.httyd_3};
        movie.setPicturesActors(pictures_and_actors_httyd);
        movieList.add(new Model(Model.RIGHT_SIDE, movie));

        movie = new Movie(getString(R.string.title_legally_blonde), getString(R.string.genre_ad_co), 2002, R.drawable.legally_blond, R.string.des_legally_blonde);
        Integer[] pictures_and_actors_legally = {R.drawable.legally_01, R.drawable.legally_02, R.drawable.legally_03, R.drawable.legally_04, R.drawable.legally_05, R.drawable.legally_06,
                R.drawable.legally_1, R.drawable.legally_2, R.drawable.legally_3, R.string.legally_1, R.string.legally_2, R.string.legally_3};
        movie.setPicturesActors(pictures_and_actors_legally);
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
