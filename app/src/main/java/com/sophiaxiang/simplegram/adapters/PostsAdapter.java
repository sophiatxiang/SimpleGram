package com.sophiaxiang.simplegram.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.sophiaxiang.simplegram.DetailsActivity;
import com.sophiaxiang.simplegram.fragments.UserDetailsFragment;
import com.sophiaxiang.simplegram.models.Post;
import com.sophiaxiang.simplegram.R;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTime;
        private boolean hasImage;
        private String imageUrl;
        private ImageView ivProfilePic;
        private boolean hasProfileImage;
        private String profileImageUrl;
        private RelativeLayout rlUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlUser = itemView.findViewById(R.id.rlUser);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            rlUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity)context;
                    Fragment nextFrag= new UserDetailsFragment();
                    Bundle bundle = new Bundle();
                    Post post = posts.get(getAdapterPosition());
                    bundle.putSerializable("post", post);
                    nextFrag.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer, nextFrag)
                            .addToBackStack(null)
                            .commit();
                }
            });
            itemView.setOnClickListener(this);
        }

        // binds post data into the view elements
        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                hasImage = true;
                imageUrl = image.getUrl();
                Glide.with(context).load(imageUrl).into(ivImage);
            }
            else hasImage = false;

            ParseFile profileImage = post.getProfileImage();
            if (profileImage != null) {
                hasProfileImage = true;
                profileImageUrl = profileImage.getUrl();
                Glide.with(context).load(profileImageUrl).circleCrop().into(ivProfilePic);
            }
            else hasProfileImage = false;

            String timeAgo = Post.calculateTimeAgo(post.getCreatedAt());
            tvTime.setText(timeAgo);
        }

        // if ViewHolder is clicked, launch post details screen
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "CLICKED", Toast.LENGTH_SHORT).show();
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the tweet at the position, this won't work if the class is static
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("username", tvUsername.getText());
                intent.putExtra("description", tvDescription.getText());
                intent.putExtra("time", tvTime.getText());
                if (hasImage) {
                    intent.putExtra("imageUrl", imageUrl);
                    intent.putExtra("hasImage", true);
                } else intent.putExtra("hasImage", false);

                if (hasProfileImage) {
                    intent.putExtra("profileImageUrl", profileImageUrl);
                    intent.putExtra("hasProfileImage", true);
                } else intent.putExtra("hasProfileImage", false);
                context.startActivity(intent);
            }
        }
    }
}
