package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.bean.Posts;
import com.example.app.bean.Sports;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyHolder>{
    private View view;
    private Context context;
    private List<Posts> postsList;

    public PostsAdapter(Context context, List<Posts> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_posts, parent, false);
        PostsAdapter.MyHolder myHolder= new PostsAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.postUsername.setText(postsList.get(position).getPostUsername());
        holder.postTime.setText(postsList.get(position).getPostTime());
        holder.postContent.setText(postsList.get(position).getPostContent());
    }


    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private TextView postUsername;
        private TextView postTime;
        private TextView postContent;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            postUsername = (TextView) itemView.findViewById(R.id.postUsername);
            postTime = (TextView) itemView.findViewById(R.id.postTime);
            postContent = (TextView) itemView.findViewById(R.id.postContent);
        }
    }
}
