package edu.ubb.movie_app.moviebile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class MainMovieListActivity extends AppCompatActivity {

    private Button mLogOutButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainMovieListActivity.this, ChooserActivity.class));
                }
            }
        };
        mLogOutButton = findViewById(R.id.signOutButton);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void viewMovies(View view) {
        Log.w("USER", mAuth.getCurrentUser().getEmail());

        if (mAuth.getCurrentUser().getEmail().equals("ecaterina.carazan@gmail.com")) {
            Intent intent = new Intent(this, AdminMoviesActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, FreeUserMoviesActivity.class); // free user
            startActivity(intent);
        }
    }
}
