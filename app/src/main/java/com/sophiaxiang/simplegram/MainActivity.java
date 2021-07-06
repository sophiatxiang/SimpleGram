package com.sophiaxiang.simplegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogoutButton(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        Toast.makeText(MainActivity.this, "Logout success!", Toast.LENGTH_SHORT).show();
        goOpeningActivity();
    }

    private void goOpeningActivity() {
        Intent intent = new Intent(this, OpeningActivity.class);
        startActivity(intent);
        finish();
    }
}