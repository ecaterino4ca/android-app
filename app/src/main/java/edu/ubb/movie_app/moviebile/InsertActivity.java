package edu.ubb.movie_app.moviebile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ecaterina on 12/2/17.
 */

public class InsertActivity extends Activity {

    private Button save_insert_button;
    private MovieRepositoryImpl movieRepository = new MovieRepositoryImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        EditText titleTextView = findViewById(R.id.movie_insert_title);
        EditText yearTextView = findViewById(R.id.movie_insert_year);
        EditText ratingTextView = findViewById(R.id.movie_insert_rating);
        EditText directorTextView = findViewById(R.id.movie_insert_director);
        EditText castTextView = findViewById(R.id.movie_insert_cast);
        EditText genresTextView = findViewById(R.id.movie_insert_genre);

        save_insert_button = findViewById(R.id.save_insert_button);
        save_insert_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveData();
                    }
                }
        );
    }

    private void saveData(){
        EditText titleTextView = findViewById(R.id.movie_insert_title);
        EditText yearTextView = findViewById(R.id.movie_insert_year);
        EditText ratingTextView = findViewById(R.id.movie_insert_rating);
        EditText directorTextView = findViewById(R.id.movie_insert_director);
        EditText castTextView = findViewById(R.id.movie_insert_cast);
        EditText genresTextView = findViewById(R.id.movie_insert_genre);

        Movie movie = new Movie(titleTextView.getText().toString(), yearTextView.getText().toString(), Integer.valueOf(ratingTextView.getText().toString()),
                genresTextView.getText().toString(),castTextView.getText().toString(), directorTextView.getText().toString());

        movieRepository.insert(movie);

        Intent myIntent = new Intent(InsertActivity.this, MainMovieListActivity.class);
        InsertActivity.this.startActivity(myIntent);
    }
}
