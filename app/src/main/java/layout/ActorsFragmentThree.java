package layout;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import witkowska.app2.Model;
import witkowska.app2.Movie;
import witkowska.app2.R;

public class ActorsFragmentThree extends Fragment {

    private List<Model> movieList = new ArrayList<>();
    Movie movie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle movie_data = getArguments();
            movieList = (ArrayList<Model>) movie_data.getSerializable("Movies");
            final int position = movie_data.getInt("Position");
            movie = movieList.get(position).getMovie();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.actors_fragment_three, container, false);

        Integer[] actors = movie.getActors();

        ImageView picture1 = (ImageView) view.findViewById(R.id.picture_1);
        ImageView picture2 = (ImageView) view.findViewById(R.id.picture_2);
        ImageView picture3 = (ImageView) view.findViewById(R.id.picture_3);
        TextView actor1 = (TextView) view.findViewById(R.id.author_name_1);
        TextView actor2 = (TextView) view.findViewById(R.id.author_name_2);
        TextView actor3 = (TextView) view.findViewById(R.id.author_name_3);

        picture1.setImageResource(actors[0]);
        picture2.setImageResource(actors[1]);
        picture3.setImageResource(actors[2]);
        actor1.setText(getString(actors[3]));
        actor2.setText(getString(actors[4]));
        actor3.setText(getString(actors[5]));

        return view;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//
//    }

}
