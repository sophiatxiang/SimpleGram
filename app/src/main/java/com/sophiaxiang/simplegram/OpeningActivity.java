package com.sophiaxiang.simplegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class OpeningActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        // if already logged in, go straight to main activity
        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        btnLogin = findViewById(R.id.btnLogin);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
    }

    // if btnLogin is clicked, launch Login activity
    public void launchLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // if btnCreateAccount is clicked, launch create account activity
    public void launchCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccActivity.class);
        startActivity(intent);
    }

    // launches main activity and finishes/closes opening activity
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}