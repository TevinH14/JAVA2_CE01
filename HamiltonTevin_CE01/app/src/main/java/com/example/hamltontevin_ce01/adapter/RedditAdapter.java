//Tevin Hamilton
//term:2003
//CE01
package com.example.hamltontevin_ce01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hamltontevin_ce01.R;
import com.example.hamltontevin_ce01.model.RedditPost;

import java.util.ArrayList;

public class RedditAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<RedditPost> mRedditList;

    public RedditAdapter(Context mContext, ArrayList<RedditPost> mRedditList) {
        this.mContext = mContext;
        this.mRedditList = mRedditList;
    }

    @Override
    public int getCount() {
        return mRedditList.size();
    }

    @Override
    public Object getItem(int pos) {
        return mRedditList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        RedditPost post = (RedditPost) getItem(position);

        if(convertView== null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.reddit_post_layout,parent,false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }
        if(post != null){
            String numCommentsString = Integer.toString(post.getNumComments());
            vh.numCount.setText(numCommentsString);
            vh.title.setText(post.getTitle());
            vh.link.setText(post.getLink());
        }
        return convertView;
    }


    static class ViewHolder{
        final TextView numCount;
        final TextView title;
        final TextView link;

        ViewHolder(View layout) {
            this.numCount = layout.findViewById(R.id.tv_numComments);
            this.title = layout.findViewById(R.id.tv_title);
            this.link = layout.findViewById(R.id.tv_link);
        }
    }


}
