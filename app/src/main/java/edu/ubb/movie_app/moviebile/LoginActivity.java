package edu.ubb.movie_app.moviebile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //TODO: store in DB, do not hardcode anymore
    private static final String[] CREDENTIALS = new String[]
            {"test@test.com:test", "ecaterina.carazan@gmail.com:catiusa"};

    private EditText email;
    private EditText password;
    private Button login_button;

    private String mEmail;
    private String mPassword;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login();
    }

    public void login() {
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        login_button = findViewById(R.id.button_login);

        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attemptLogin();
                    }
                }
        );
    }

    private void attemptLogin(){

        if (mAuthTask != null)
        {
            return;
        }

        // Reset errors.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        mEmail = email.getText().toString();
        mPassword = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword))
        {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        } else if (mPassword.length() < 3)
        {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail))
        {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!mEmail.contains("@"))
        {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else
        {
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
        }

    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            for (String credential : CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
                Toast.makeText(getApplicationContext(),"Redirecting...",
                        Toast.LENGTH_SHORT).show();
                //redirect the user to the new activity
                Intent myIntent = new Intent(LoginActivity.this, MainMovieListActivity.class);
                LoginActivity.this.startActivity(myIntent);
            } else {
                password.setError(getString(R.string.error_incorrect_password));
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

    }


}
