package edu.ubb.movie_app.moviebile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MovieDetailActivity extends Activity {

    private Button save_button;
    private Button delete_button;
    private MovieRepositoryImpl movieRepository = new MovieRepositoryImpl();
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

//        index = Integer.valueOf(this.getIntent().getExtras().getString("index"));
        String title = this.getIntent().getExtras().getString("title");
        String year =String.valueOf(this.getIntent().getExtras().getInt("year"));
        String rating = String.valueOf(this.getIntent().getExtras().getInt("rating"));
        String genres = this.getIntent().getExtras().getString("genres");
        String cast = this.getIntent().getExtras().getString("cast");
        String director = this.getIntent().getExtras().getString("director");
        id = this.getIntent().getExtras().getString("id");

        TextView titleTextView = findViewById(R.id.movie_list_title);
        EditText yearTextView = findViewById(R.id.movie_list_year);
        EditText ratingTextView = findViewById(R.id.movie_list_rating);
        EditText directorTextView = findViewById(R.id.movie_list_director);
        EditText castTextView = findViewById(R.id.movie_list_cast);
        final TextView genresTextView = findViewById(R.id.movie_list_genre);

        final NumberPicker genresPicker = findViewById(R.id.picker);
        final String genresList[] = { "comedy", "thriller", "horror", "drama", "documentary" };
        genresPicker.setMinValue(0);
        genresPicker.setMaxValue(genresList.length - 1);
        genresPicker.setDisplayedValues(genresList);
        genresPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        genresPicker.setVisibility(View.GONE);

        NumberPicker.OnClickListener myValChangedListener = new NumberPicker.OnClickListener() {

            @Override
            public void onClick(View view) {
                String selected = genresList[genresPicker.getValue()];
                genresTextView.setText(selected);
                genresPicker.setVisibility(View.GONE);
            }
        };

        genresPicker.setOnClickListener(myValChangedListener);

        genresTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genresPicker.setVisibility(View.VISIBLE);
            }
        });

        titleTextView.setText(title);
        yearTextView.setText(year);
        ratingTextView.setText(rating);
        castTextView.setText(cast);
        directorTextView.setText(director);
        genresTextView.setText(genres);

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveData();
                    }
                }
        );
        delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteData();
                    }
                }
        );
    }

    private void saveData(){
        TextView titleTextView = findViewById(R.id.movie_list_title);
        EditText yearTextView = findViewById(R.id.movie_list_year);
        EditText ratingTextView = findViewById(R.id.movie_list_rating);
        EditText directorTextView = findViewById(R.id.movie_list_director);
        EditText castTextView = findViewById(R.id.movie_list_cast);
        final TextView genresTextView = findViewById(R.id.movie_list_genre);

        Movie movie = new Movie(titleTextView.getText().toString(), Integer.valueOf(yearTextView.getText().toString()), Integer.valueOf(ratingTextView.getText().toString()),
                genresTextView.getText().toString(),castTextView.getText().toString(), directorTextView.getText().toString());

        movie.setId(id);
        movieRepository.update(movie);

        Intent myIntent = new Intent(MovieDetailActivity.this, MainMovieListActivity.class);
        MovieDetailActivity.this.startActivity(myIntent);
    }

    private void deleteData(){
        TextView titleTextView = findViewById(R.id.movie_list_title);
        EditText yearTextView = findViewById(R.id.movie_list_year);
        EditText ratingTextView = findViewById(R.id.movie_list_rating);
        EditText directorTextView = findViewById(R.id.movie_list_director);
        EditText castTextView = findViewById(R.id.movie_list_cast);
        final TextView genresTextView = findViewById(R.id.movie_list_genre);

        Movie movie = new Movie(titleTextView.getText().toString(), Integer.valueOf(yearTextView.getText().toString()), Integer.valueOf(ratingTextView.getText().toString()),
                genresTextView.getText().toString(),castTextView.getText().toString(), directorTextView.getText().toString());

        movie.setId(id);

        movieRepository.delete(movie);
        Toast.makeText(this,"Movie Removed",Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(MovieDetailActivity.this, MainMovieListActivity.class);
        MovieDetailActivity.this.startActivity(myIntent);
    }

}
