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
import witkowska.app2.R;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.*;
import butterknife.OnClick;


import static android.content.Context.MODE_PRIVATE;

public class DescriptionFragmentOne extends Fragment {

    private List<Model> movieList = new ArrayList<>();
    float saved_rating = 0;
    Bundle movie_data;
    Movie movie;
    RatingBar rating_bar;

//    @BindView(R.id.title) TextView title_tv;
//    @BindView(R.id.genre) TextView genre_tv;
//    @BindView(R.id.year) TextView year_tv;
//    @BindView(R.id.description) TextView description_tv;
//    @BindView(R.id.rating_bar) RatingBar rating_bar;
//    @BindView(R.id.picture) ImageView picture;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public DescriptionFragmentOne() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DescriptionFragmentOne.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DescriptionFragmentOne newInstance(String param1, String param2) {
//        DescriptionFragmentOne fragment = new DescriptionFragmentOne();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View description_view = inflater.inflate(R.layout.description_fragment_one, container,false);
        return description_view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {

        ImageView image = (ImageView) view.findViewById(R.id.picture);
        TextView title_tv = (TextView) view.findViewById(R.id.title);
        TextView genre_tv = (TextView) view.findViewById(R.id.genre);
        TextView year_tv = (TextView) view.findViewById(R.id.year);
        final TextView description_tv = (TextView) view.findViewById(R.id.description);
        rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
        ImageView picture = (ImageView) view.findViewById(R.id.picture);

        movie_data = getArguments();
        movieList = (ArrayList<Model>) movie_data.getSerializable("Movies");
        final int position = movie_data.getInt("Position");

        movie = movieList.get(position).getMovie();

        title_tv.setText(movie.getTitle());
        genre_tv.setText(movie.getGenre());
        year_tv.setText(String.valueOf(movie.getYear()));
        description_tv.setText(getString(movie.getDescriptionResource()));
        picture.setImageResource(movie.getPictureResource());
        rating_bar.setRating(movie.getRating());

//        loadDatafromBundle();

        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                saved_rating = rating;
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if (savedInstanceState == null) {
                    PhotosFragmentTwo fragment_two = new PhotosFragmentTwo();
                    fragment_two.setArguments(movie_data);
                    ActorsFragmentThree fragment_three = new ActorsFragmentThree();
                    fragment_three.setArguments(movie_data);

                    ft.replace(R.id.fragment_container, fragment_two);
                    ft.add(R.id.fragment_container2, fragment_three);
                    ft.commit();
                }

//                Toast.makeText(getActivity(), "It works from onClickListener", Toast.LENGTH_SHORT).show();
            }

        });


    }

//    public void onPause() {
////        Intent intent = new Intent(getActivity(), MainActivity.class);
////        intent.putExtra("FragmentRating", saved_rating);
////        startActivity(intent);
//
//        Toast.makeText(getActivity(), "Rating = " + String.valueOf(saved_rating), Toast.LENGTH_SHORT).show();
//        super.onPause();
//    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putFloat("FragmentRating", saved_rating);
//        //Save the fragment's state here
//    }

//    public void pictureClick(View view) {
//        Toast.makeText(getActivity(), "It works!", Toast.LENGTH_SHORT).show();
//
//    }

    public float getRating(){
        return saved_rating;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("Rating", saved_rating);
//    }



//    public void setRating(float r) {
//        rating_bar.setRating(r);
//    }
//
////    public void loadDatafromBundle() {
//        Bundle movie_data = getArguments();
//        movieList = (ArrayList<Model>) movie_data.getSerializable("Movies");
//        final int position = movie_data.getInt("Position");
//
//        movie = movieList.get(position).getMovie();
//
//        title_tv.setText(movie.getTitle());
//        genre_tv.setText(movie.getGenre());
//        year_tv.setText(movie.getYear());
//        description_tv.setText(getString(movie.getDescriptionResource()));
//        picture.setImageResource(movie.getPictureResource());
//    }






//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.description_fragment_one, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }



}
