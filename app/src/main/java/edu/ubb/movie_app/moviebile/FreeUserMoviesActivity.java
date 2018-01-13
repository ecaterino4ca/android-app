package edu.ubb.movie_app.moviebile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class FreeUserMoviesActivity extends AppCompatActivity implements RepositoryObserver{
    private ListView mListView;
    List<Movie> movieList;
    private Button report_button;
    private Button chart_button;
    private EditText input_bug;
    private String reportText;

    private Subject movieRepository = MovieRepositoryImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_user_movie_activity);

        movieRepository.registerObserver(this);
        mListView = findViewById(R.id.mylist);
        movieList = movieRepository.getMovieList();

        String[] listItems = new String[movieList.size()];
        for (int i = 0; i < movieList.size(); i++) {
            Movie movie = movieList.get(i);
            listItems[i] = movie.getTitle();
            listItems[i] = movie.getYear().toString();
            listItems[i] = String.valueOf(movie.getRating());
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

        input_bug = findViewById(R.id.input_bug);
        report_button = findViewById(R.id.report_button);
        report_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmail();
                    }
                }
        );

        chart_button = findViewById(R.id.chart_button);

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

                MovieAdapter movieAdapter = new MovieAdapter(FreeUserMoviesActivity.this, movieList);
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

    public void sendEmail() {
        reportText = input_bug.getText().toString();
        View focusView = null;

        if (TextUtils.isEmpty(reportText)) {
            input_bug.setError(getString(R.string.error_field_required));
            focusView = input_bug;
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ecaterina.carazan@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Report a bug");
            intent.putExtra(Intent.EXTRA_TEXT, input_bug.getText());
            intent.setPackage("com.google.android.gm");
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
            else
                Toast.makeText(this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
        }
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
        Toast.makeText(FreeUserMoviesActivity.this, data, Toast.LENGTH_SHORT).show();
    }
}
