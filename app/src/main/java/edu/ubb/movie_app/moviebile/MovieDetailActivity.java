package edu.ubb.movie_app.moviebile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MovieDetailActivity extends Activity {

    private Button save_button;
    private MovieRepositoryImpl movieRepository = MovieRepositoryImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String title = this.getIntent().getExtras().getString("title");
        String year = this.getIntent().getExtras().getString("year");
        String rating = this.getIntent().getExtras().getString("rating");
        String genres = this.getIntent().getExtras().getString("genres");
        String cast = this.getIntent().getExtras().getString("cast");
        String director = this.getIntent().getExtras().getString("director");

        TextView titleTextView = findViewById(R.id.movie_list_title);
        EditText yearTextView = findViewById(R.id.movie_list_year);
        EditText ratingTextView = findViewById(R.id.movie_list_rating);
        EditText directorTextView = findViewById(R.id.movie_list_director);
        EditText castTextView = findViewById(R.id.movie_list_cast);
        EditText genresTextView = findViewById(R.id.movie_list_genre);

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
    }

    private void saveData(){
        TextView titleTextView = findViewById(R.id.movie_list_title);
        EditText yearTextView = findViewById(R.id.movie_list_year);
        EditText ratingTextView = findViewById(R.id.movie_list_rating);
        EditText directorTextView = findViewById(R.id.movie_list_director);
        EditText castTextView = findViewById(R.id.movie_list_cast);
        EditText genresTextView = findViewById(R.id.movie_list_genre);

        Movie movie = new Movie(titleTextView.getText().toString(), yearTextView.getText().toString(), ratingTextView.getText().toString(),
                genresTextView.getText().toString(),castTextView.getText().toString(), directorTextView.getText().toString());

        movieRepository.update(movie);

        Intent myIntent = new Intent(MovieDetailActivity.this, MainMovieListActivity.class);
        MovieDetailActivity.this.startActivity(myIntent);
    }

}
