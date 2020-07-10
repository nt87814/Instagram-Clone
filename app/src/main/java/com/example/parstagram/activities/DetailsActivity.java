package com.example.parstagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parstagram.R;
import com.example.parstagram.models.Post;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    Post updatedPost;

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;

    private static final String TAG = "DetailsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDesciption);

//        tvUsername.setText(post.getUser().getUsername());
//        Glide.with(this).load(post.getImage()).into(ivImage);
//        tvDescription.setText(post.getDescription());
    }
}