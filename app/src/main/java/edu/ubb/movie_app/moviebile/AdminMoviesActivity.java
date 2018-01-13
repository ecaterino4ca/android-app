package edu.ubb.movie_app.moviebile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by ecaterina on 1/13/18.
 */

public class AdminMoviesActivity extends AppCompatActivity implements RepositoryObserver{
    private ListView mListView;
    List<Movie> movieList;
    private Button insert_button;
    private Button chart_button;

    private Subject movieRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_movies_activity);

        mListView = findViewById(R.id.list);
        movieRepository = MovieRepositoryImpl.getInstance();
        movieRepository.registerObserver(this);
        movieList = movieRepository.getMovieList();

        MovieAdapter adapter = new MovieAdapter(this, movieList);
        mListView.setAdapter(adapter);

        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectOneItem(context, adapterView, view, i, l);
            }

        });

        insert_button = findViewById(R.id.admin_insert_button);
        insert_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createDialog();
                    }
                }
        );

        chart_button = findViewById(R.id.admin_chart_button);

        chart_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayChart();
                    }
                }
        );


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("movies/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                movieList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Movie recipe = postSnapshot.getValue(Movie.class);
                    movieList.add(recipe);
                }

                MovieAdapter movieAdapter = new MovieAdapter(AdminMoviesActivity.this, movieList);
                mListView.setAdapter(movieAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        detailIntent.putExtra("id", selectedMovie.getId());

        startActivity(detailIntent);
    }

    public void redirectInsert(){
        Toast.makeText(getApplicationContext(),"Redirecting...",
                Toast.LENGTH_SHORT).show();
        //redirect the user to the new activity
        Intent myIntent = new Intent(AdminMoviesActivity.this, InsertActivity.class);
        AdminMoviesActivity.this.startActivity(myIntent);
    }

    public void createDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Insert a new movie here");

        final Context context = alertDialogBuilder.getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleTextView = new EditText(context);
        final EditText yearTextView = new EditText(context);
        final EditText ratingTextView = new EditText(context);
        final EditText directorTextView = new EditText(context);
        final EditText castTextView = new EditText(context);
        final TextView genresTextView = new TextView(context);

        final NumberPicker genresPicker = new NumberPicker(context);
        final String genres[] = { "comedy", "thriller", "horror", "drama", "documentary" };
        genresPicker.setMinValue(0);
        genresPicker.setMaxValue(genres.length - 1);
        genresPicker.setDisplayedValues(genres);
        genresPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        NumberPicker.OnClickListener myValChangedListener = new NumberPicker.OnClickListener() {

            @Override
            public void onClick(View view) {
                String selected = genres[genresPicker.getValue()];
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

        titleTextView.setHint("title");
        yearTextView.setHint("year");
        ratingTextView.setHint("rating");
        directorTextView.setHint("director");
        castTextView.setHint("cast");
        genresTextView.setHint("genres");

        layout.addView(titleTextView);
        layout.addView(yearTextView);
        layout.addView(ratingTextView);
        layout.addView(directorTextView);
        layout.addView(castTextView);
        layout.addView(genresTextView);
        layout.addView(genresPicker);
        genresPicker.setVisibility(View.GONE);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        Movie movie = new Movie(titleTextView.getText().toString(), Integer.valueOf(yearTextView.getText().toString()), Integer.valueOf(ratingTextView.getText().toString()),
                                genresTextView.getText().toString(),castTextView.getText().toString(), directorTextView.getText().toString());

                        movieRepository.insert(movie);
                        startActivity(getIntent());
                    }
                });

        alertDialogBuilder.setNegativeButton("Discard",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void displayChart(){
        Map<String, Integer> data = movieRepository.getChartData();

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int count = 0;
        for (Integer value : data.values()){
            entries.add(new BarEntry(value, count));
            count++;
        }

        for (String key: data.keySet()){
            labels.add(key);
        }

        BarDataSet dataset = new BarDataSet(entries, "Average rating");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        BarChart chart = new BarChart(this);
        setContentView(chart);
        BarData barData = new BarData(labels, dataset);
        chart.setData(barData);
        chart.setDescription("The Average populatiry of different genres");

    }

    @Override
    public void onDataChanged(String data) {
        Toast.makeText(AdminMoviesActivity.this, data, Toast.LENGTH_SHORT).show();
    }
}
