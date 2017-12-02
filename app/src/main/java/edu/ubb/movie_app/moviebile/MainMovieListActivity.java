package edu.ubb.movie_app.moviebile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainMovieListActivity extends Activity {

    private ListView mListView;
    List<Movie> movieList;
    private Button report_button;
    private Button insert_button;
    private Button chart_button;
    private EditText input_bug;
    private String reportText;

    private MovieRepositoryImpl movieRepository = new MovieRepositoryImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);

        mListView =  findViewById(R.id.list);
        movieList = movieRepository.getMovieList();

        String[] listItems = new String[movieList.size()];
        for(int i = 0; i < movieList.size(); i++){
            Movie movie = movieList.get(i);
            listItems[i] = movie.getTitle();
            listItems[i] = movie.getYear();
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

        insert_button = findViewById(R.id.insert_button);
        insert_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        redirectInsert();
                        createDialog();
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

    public void redirectInsert(){
        Toast.makeText(getApplicationContext(),"Redirecting...",
                Toast.LENGTH_SHORT).show();
        //redirect the user to the new activity
        Intent myIntent = new Intent(MainMovieListActivity.this, InsertActivity.class);
        MainMovieListActivity.this.startActivity(myIntent);
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
                        Movie movie = new Movie(titleTextView.getText().toString(), yearTextView.getText().toString(), Integer.valueOf(ratingTextView.getText().toString()),
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

}
