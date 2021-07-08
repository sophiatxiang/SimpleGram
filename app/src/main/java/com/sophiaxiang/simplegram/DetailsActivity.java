package com.sophiaxiang.simplegram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvTime = findViewById(R.id.tvTime);

        String username = getIntent().getStringExtra("username");
        String description = getIntent().getStringExtra("description");
        String time = getIntent().getStringExtra("time");

        if (getIntent().getBooleanExtra("hasImage", false)) {
            String imageUrl = getIntent().getStringExtra("imageUrl");
            if (imageUrl != null) {
                Glide.with(this).load(imageUrl).into(ivImage);
            }
        }

        tvDescription.setText(description);
        tvUsername.setText(username);
        tvTime.setText(time);
    }
}