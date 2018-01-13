package edu.ubb.movie_app.moviebile;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ecaterina on 10/28/17.
 */

public class MovieRepositoryImpl implements Subject{

    private FirebaseDatabase database;
    private static final String TAG = "REPOSITORY";
    private List<Movie> movieList = new ArrayList<>();
    private static MovieRepositoryImpl INSTANCE = null;
    private ArrayList<RepositoryObserver> mObservers;

    public MovieRepositoryImpl() {
        mObservers = new ArrayList<>();
        if(database == null){
            database = FirebaseDatabase.getInstance();
//            database.setPersistenceEnabled(true);
        }
        addGetAllListener();
    }

    // Creates a Singleton of the class
    public static MovieRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MovieRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public void insert(Movie movie) {
//        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies/");
        myRef.keepSynced(true);
        String id = myRef.push().getKey();
        movie.setId(id);
        myRef.child(id).setValue(movie);
        notifyObservers();
    }

    @Override
    public void delete(Movie movie) {
//        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies/");
        myRef.keepSynced(true);
        myRef.child(movie.getId()).removeValue();
    }

    @Override
    public void update(Movie movie) {
//        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies/");
        myRef.keepSynced(true);
        myRef.child(movie.getId()).setValue(movie);
    }

    @Override
    public List<Movie> getMovieList() {
        return movieList;
    }

    public void addGetAllListener() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies/");
        myRef.keepSynced(true);
        ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
            movieList.clear();

            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    movieList.add(postSnapshot.getValue(Movie.class));
                }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
        };
        myRef.addValueEventListener(postListener);

    }

    @Override
    public Map<String, Integer> getChartData(){
        Map<String, Integer> result = new HashMap<>();
        List<Movie> movies = movieList;
        List<String> genres = new ArrayList<>();

        for (Movie movie:movies) {
            if (!genres.contains(movie.getGenres())){
                genres.add(movie.getGenres());
            }
        }

        for (String genre:genres) {
            result.put(genre, calculateAverageRating(genre, movies));
        }
        return result;
    }

    private Integer calculateAverageRating(String genre, List<Movie> movies){
        Integer sum = 0;
        Integer count = 0;
        for (Movie movie : movies) {
            if (movie.getGenres().equals(genre)){
                sum += movie.getRating();
                count++;
            }
        }

        return sum/count;
    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if(!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        if(mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer: mObservers) {
            observer.onDataChanged("A new movie was added");
        }
    }


}
