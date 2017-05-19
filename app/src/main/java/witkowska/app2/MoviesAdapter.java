package witkowska.app2;

/**
 * Created by Administrator on 2017-04-11.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Model> moviesList;
    int total_types;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public ImageView picture, eye;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            picture = (ImageView) view.findViewById(R.id.picture);
            eye = (ImageView) view.findViewById(R.id.eye);
        }
    }

    public MoviesAdapter(List<Model> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public int getItemViewType(int position) {
        switch(moviesList.get(position).type) {
            case 0:
                return Model.LEFT_SIDE;
            case 1:
                return Model.RIGHT_SIDE;
            default:
                return -1;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case Model.LEFT_SIDE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
                break;
            case Model.RIGHT_SIDE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_movie_list_row, parent, false);
                break;
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position).getMovie();
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(String.valueOf(movie.getYear()));
        holder.picture.setImageResource(movie.getPictureResource());
        //holder.eye.setImageResource(movie.getEyeResource());
        if (!movie.getHasBeenSeen())
            holder.eye.setImageResource(R.drawable.eye);
        else
            holder.eye.setImageResource(R.drawable.empty);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}