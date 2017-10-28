package edu.ubb.movie_app.moviebile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ecaterina on 10/28/17.
 */

public class MovieAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Movie> movieArrayList;

    public MovieAdapter(Context context, List<Movie> items) {
        mContext = context;
        movieArrayList = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movieArrayList.size();
    }

    @Override
    public Movie getItem(int position) {
        return movieArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.movie_list_item, viewGroup, false);

        TextView titleTextView = rowView.findViewById(R.id.movie_list_title);

        Movie movie = getItem(position);

        titleTextView.setText(movie.getTitle());

        return rowView;
    }
}
