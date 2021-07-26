package com.example.hamltontevin_ce01.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.hamltontevin_ce01.R;
import com.example.hamltontevin_ce01.adapter.RedditAdapter;
import com.example.hamltontevin_ce01.model.RedditPost;

import java.util.ArrayList;

public class PostListFragment extends ListFragment {

    private  static ArrayList<RedditPost> mRedditPostArray;
    public PostListFragment() {
    }

    public static PostListFragment newInstance(ArrayList<RedditPost> redditPosts) {

        Bundle args = new Bundle();
        mRedditPostArray =redditPosts;

        PostListFragment fragment = new PostListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reddit_display_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if( mRedditPostArray != null && mRedditPostArray.size() > 0){
            RedditAdapter ra = new RedditAdapter(getContext(),mRedditPostArray);
            setListAdapter(ra);
        }
    }
}
