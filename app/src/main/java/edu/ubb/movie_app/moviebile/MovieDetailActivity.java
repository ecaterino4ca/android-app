package edu.ubb.movie_app.moviebile;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class MovieDetailActivity extends Activity {

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
        TextView yearTextView = findViewById(R.id.movie_list_year);
        TextView ratingTextView = findViewById(R.id.movie_list_rating);
        TextView directorTextView = findViewById(R.id.movie_list_director);
        TextView castTextView = findViewById(R.id.movie_list_cast);
        TextView genresTextView = findViewById(R.id.movie_list_genre);

        titleTextView.setText(title);
        yearTextView.setText(year);
        ratingTextView.setText(rating);
        castTextView.setText(cast);
        directorTextView.setText(director);
        genresTextView.setText(genres);

    }
}
