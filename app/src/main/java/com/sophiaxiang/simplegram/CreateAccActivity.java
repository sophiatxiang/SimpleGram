package com.sophiaxiang.simplegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateAccActivity extends AppCompatActivity {

    public static final String TAG = "CreateAccActivity";
    EditText etUsername;
    EditText etPassword;
    EditText etEmail;
    Button btnCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnCreateUser = findViewById(R.id.btnCreateUser);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                createUser(username, password, email);
            }
        });
    }

    // creates new Parse user account
    private void createUser(String username, String password, String email) {
        Log.i(TAG, "attempting to create user " + username);
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                // if user creation succeeds
                if (e == null) {
                    goLoginActivity();
                    Toast.makeText(CreateAccActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }
                // if user creation fails
                else {
                    Log.e(TAG, "Issue with user creation", e);
                    Toast.makeText(CreateAccActivity.this, "Issue with account creation!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    // go to login
    private void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}