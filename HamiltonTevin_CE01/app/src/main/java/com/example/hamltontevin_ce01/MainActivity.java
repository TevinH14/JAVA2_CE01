package com.example.hamltontevin_ce01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hamltontevin_ce01.fragment.PostListFragment;
import com.example.hamltontevin_ce01.model.RedditPost;
import com.example.hamltontevin_ce01.network.NetworkTask;
import com.example.hamltontevin_ce01.network.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;

import fileStorage.RedditFile;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NetworkTask.OnFinished {

    private static final String ARMA3_API ="https://www.reddit.com/r/arma3/hot.json";
    private static final String IPHONE_API = "https://www.reddit.com/r/iphone/hot.json";
    private static final String ANDROID_API = "https://www.reddit.com/r/android/hot.json";
    private static final String XBOX_API = "https://www.reddit.com/r/xbox/hot.json";
    private static final String PLAYSTAION_API = "https://www.reddit.com/r/playstation/hot.json";
    private static final String APP_API = "https://www.reddit.com/r/apps/hot.json";
    private static final String MOVIES_API = "https://www.reddit.com/r/movies/hot.json";

    private String[] peopleArray;

    //private boolean internetStatus = true;
    private boolean fragmentAdded = false;
    private String currentSubReddit = null;
    private  RedditFile mRedditFile = null;

    private HashMap<String,ArrayList<RedditPost>> redditHashMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redditHashMap = new HashMap<>();
        checkInternet();

        populateSpinner();

        //create a new reddit fiel to be able to save and load data
        mRedditFile = new RedditFile(this);

        // load in the first sub reddit into the the app
       userSelection(0);
    }
    // used to allow user to select what api they would like to view.

    //spinner selection.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        currentSubReddit = peopleArray[pos];
        if(!redditHashMap.containsKey(currentSubReddit)){
           userSelection(pos);
        }
        else{
            updateFragment(redditHashMap.get(currentSubReddit));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //after retrieving data update the fragment , add the data to a HashMap then save the data
    @Override
    public void OnPost(ArrayList<RedditPost> redditPosts) {
        updateFragment(redditPosts);
        redditHashMap.put(currentSubReddit,redditPosts);
        mRedditFile.SaveData(redditHashMap.get(currentSubReddit),currentSubReddit);
    }

    //populate spinner with a string-array resource and array adapter to populate.
    private void populateSpinner(){
        Spinner mainSpinner = findViewById(R.id.spinner_api);
        mainSpinner.setOnItemSelectedListener(this);
        createSpinnerItems();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,peopleArray);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mainSpinner.setAdapter(arrayAdapter);

    }

    // start an background task to be able download sub Reddit data.
    private void StartTask(String url){
        NetworkTask task = new NetworkTask(this);
        task.execute(url);
    }

    //check if devices is connected to the internet if not alert the user of internet access issue
    private boolean checkInternet(){
        boolean status = NetworkUtils.isConnected(this);
        if(!status){
            Toast.makeText(this,R.string.connection_needed,Toast.LENGTH_SHORT).show();
            //internetStatus = false;
        }
        return status;
    }

    private void updateFragment(ArrayList<RedditPost> redditPosts){
        if(!fragmentAdded) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentListViewContainer_frameLayout, PostListFragment.newInstance(null)).commit();
            fragmentAdded = true;
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListViewContainer_frameLayout, PostListFragment.newInstance(redditPosts)).commit();
        }
    }

    private void createSpinnerItems(){
        //create an array to capture resource
        peopleArray = getResources().getStringArray(R.array.apiNames);
        currentSubReddit = peopleArray[0];
    }

    private void userSelection(int pos){
        if(checkInternet()){
            switch (pos) {
                case 0:
                    StartTask(ARMA3_API);
                    break;
                case 1:
                    StartTask(IPHONE_API);

                    break;
                case 2:
                    StartTask(ANDROID_API);
                    break;
                case 3:
                    StartTask(XBOX_API);
                    break;
                case 4:
                    StartTask(PLAYSTAION_API);
                    break;
                case 5:
                    StartTask(APP_API);
                    break;
                case 6:
                    StartTask(MOVIES_API);
                    break;
            }
        }else{
          ArrayList<RedditPost> rp = mRedditFile.LoadData(currentSubReddit);
          if (rp != null && rp.size() > 0){
              redditHashMap.put(currentSubReddit,rp);
              updateFragment(rp);
          }else{
              updateFragment(null);
          }
        }
    }


}
