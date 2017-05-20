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

        import witkowska.app2.MainActivity;
        import witkowska.app2.Model;
        import witkowska.app2.Movie;
        import butterknife.BindColor;
        import butterknife.BindView;
        import butterknife.*;
        import butterknife.OnClick;

import witkowska.app2.R;


public class PhotosFragmentTwo extends Fragment {

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
        return inflater.inflate(R.layout.photos_fragment_two, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Integer[] photos = movie.getPictures();

        ImageView picture1 = (ImageView) view.findViewById(R.id.picture1);
        ImageView picture2 = (ImageView) view.findViewById(R.id.picture2);
        ImageView picture3 = (ImageView) view.findViewById(R.id.picture3);
        ImageView picture4 = (ImageView) view.findViewById(R.id.picture4);
        ImageView picture5 = (ImageView) view.findViewById(R.id.picture5);
        ImageView picture6 = (ImageView) view.findViewById(R.id.picture6);

        picture1.setImageResource(photos[0]);
        picture2.setImageResource(photos[1]);
        picture3.setImageResource(photos[2]);
        picture4.setImageResource(photos[3]);
        picture5.setImageResource(photos[4]);
        picture6.setImageResource(photos[5]);

    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("Flag", true);
//    }

}