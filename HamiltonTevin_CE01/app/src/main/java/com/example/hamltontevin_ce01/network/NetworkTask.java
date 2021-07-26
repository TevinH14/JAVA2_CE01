package com.example.hamltontevin_ce01.network;

import android.os.AsyncTask;
import com.example.hamltontevin_ce01.model.RedditPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkTask extends AsyncTask<String,String, ArrayList<RedditPost>> {

    final private OnFinished mOnFinishedInterface;

    public NetworkTask(OnFinished mOnFinishedInterface) {
        this.mOnFinishedInterface = mOnFinishedInterface;
    }

    //create a interface to pass and retrieve data to Network Task to download JSON obj
    public interface OnFinished{
        void OnPost(ArrayList<RedditPost> redditPosts);
    }

    @Override
    protected ArrayList<RedditPost> doInBackground(String... strings) {

        if(strings == null || strings.length <= 0 || strings[0].trim().isEmpty()){
            return null;
        }
        String data = null;
        try {
            data = NetworkUtils.getNetworkData(strings[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(data != null) {
            try {

                JSONObject response = new JSONObject(data);

                JSONObject postJson = response.getJSONObject("data");
                JSONArray postJSONArray = postJson.getJSONArray("children");

                ArrayList<RedditPost> postArrayList = new ArrayList<>();

                for (int i = 0; i < postJSONArray.length(); i++) {
                    JSONObject obj = postJSONArray.getJSONObject(i);
                    JSONObject postObj = obj.getJSONObject("data");

                    String title = postObj.getString("title");
                    int num_comments = postObj.getInt("num_comments");
                    String url = postObj.getString("url");

                    postArrayList.add(new RedditPost(title, url, num_comments));
                }
                // Update the UI
                return postArrayList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<RedditPost> redditPosts) {
        mOnFinishedInterface.OnPost(redditPosts);
    }
}
