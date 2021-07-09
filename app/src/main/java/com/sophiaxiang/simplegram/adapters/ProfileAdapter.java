package com.sophiaxiang.simplegram.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.sophiaxiang.simplegram.DetailsActivity;
import com.sophiaxiang.simplegram.models.Post;
import com.sophiaxiang.simplegram.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private Context context;
    private List<Post> posts;

    public ProfileAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileViewHolder holder, int position) {
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


    class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String tvUsername;
        private String tvDescription;
        private String tvTime;
        private ImageView ivPicture;
        private boolean hasImage;
        private String imageUrl;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            itemView.setOnClickListener(this);
        }

        // binds post data into the view elements
        public void bind(Post post) {
            tvDescription = post.getDescription();
            tvUsername = post.getUser().getUsername();
            ParseFile image = post.getImage();
            if (image != null) {
                hasImage = true;
                imageUrl = image.getUrl();
                Glide.with(context).load(imageUrl).into(ivPicture);
            }
            else hasImage = false;

            tvTime = Post.calculateTimeAgo(post.getCreatedAt());
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
                intent.putExtra("username", tvUsername);
                intent.putExtra("description", tvDescription);
                intent.putExtra("time", tvTime);
                if (hasImage) {
                    intent.putExtra("imageUrl", imageUrl);
                    intent.putExtra("hasImage", true);
                } else intent.putExtra("hasImage", false);
                context.startActivity(intent);
            }
        }
    }
}
