package com.sophiaxiang.simplegram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sophiaxiang.simplegram.models.Post;
import com.sophiaxiang.simplegram.adapters.ProfileAdapter;
import com.sophiaxiang.simplegram.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends PostsFragment {

    public static final String KEY_PROFILE_PIC = "profilePicture";
    protected ProfileAdapter adapter;
    protected ImageView ivProfileImage;
    protected String profileImageUrl;
    protected TextView tvName;
    protected TextView tvNumPosts;
    private Button btnChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        tvName = view.findViewById(R.id.tvName);
        tvNumPosts = view.findViewById(R.id.tvNumPosts);
        btnChange = view.findViewById(R.id.btnChangeProfile);

        allPosts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), allPosts);

        // set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvPosts.setLayoutManager(gridLayoutManager);

        queryPosts();

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                queryPosts();
                swipeContainer.setRefreshing(false);
                scrollListener.resetState();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment nextFrag = new ComposeProfilePicFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContainer, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // set toolbar as the action bar in this activity
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }



    // query only the posts created by the current user
    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                }
                    adapter.clear();
                for (Post post: posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                // save received posts to list and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                ParseFile profileImage = ParseUser.getCurrentUser().getParseFile(KEY_PROFILE_PIC);
                if (profileImage != null) {
                    profileImageUrl = profileImage.getUrl();
                    Glide.with(getContext()).load(profileImageUrl).circleCrop().into(ivProfileImage);
                }
                tvName.setText(ParseUser.getCurrentUser().getUsername());
                tvNumPosts.setText("" + allPosts.size());
            }
        });
    }
}
