package edu.ubb.movie_app.moviebile;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainMovieListActivity extends Activity {


    private ListView mListView;
    List<Movie> movieList;

    private MovieRepository movieRepository = new MovieRepositoryImpl();

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);

        mListView =  findViewById(R.id.list);
        movieList = movieRepository.getAllMovies();

        String[] listItems = new String[movieList.size()];
        for(int i = 0; i < movieList.size(); i++){
            Movie movie = movieList.get(i);
            listItems[i] = movie.getTitle();
            listItems[i] = String.valueOf(movie.getYear());
            listItems[i] = movie.getRating();
            listItems[i] = movie.getDirector();
            listItems[i] = movie.getCast();
        }

        MovieAdapter adapter = new MovieAdapter(this, movieList);
        mListView.setAdapter(adapter);

        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectOneItem(context, adapterView, view, i, l);

            }

        });
    }

    private void selectOneItem(Context context, AdapterView<?> adapterView, View view, int i, long l){
        Movie selectedMovie = movieList.get(i);

        Intent detailIntent = new Intent(context, MovieDetailActivity.class);

        detailIntent.putExtra("title", selectedMovie.getTitle());
        detailIntent.putExtra("year", selectedMovie.getYear());
        detailIntent.putExtra("rating", selectedMovie.getRating());
        detailIntent.putExtra("genres", selectedMovie.getGenres());
        detailIntent.putExtra("cast", selectedMovie.getCast());
        detailIntent.putExtra("director", selectedMovie.getDirector());

        startActivity(detailIntent);
    }

}
