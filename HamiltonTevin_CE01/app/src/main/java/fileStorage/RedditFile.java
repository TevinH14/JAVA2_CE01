package fileStorage;

import android.content.Context;

import com.example.hamltontevin_ce01.model.RedditPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RedditFile {
    private static final String FILE_TYPE = ".ser";

    private Context mContext;

    public RedditFile(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<RedditPost> LoadData(String currentSubReddit){
        ArrayList rd = new ArrayList<>();
        File file = mContext.getFileStreamPath(currentSubReddit+FILE_TYPE);
        if(file.exists()){

            try {
                FileInputStream fis = mContext.openFileInput(currentSubReddit + FILE_TYPE);
                ObjectInputStream ois = new ObjectInputStream(fis);
               Object object = ois.readObject();

                   rd = ((ArrayList)object);

                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            return null;
        }
        return rd;
    }


    public void SaveData(ArrayList<RedditPost> saveRedditPost, String currentSubReddit){
        if(mContext != null) {
            File file = mContext.getFileStreamPath(currentSubReddit + FILE_TYPE);
            if (saveRedditPost != null) {
                if (!file.exists() && saveRedditPost.size() > 0) {
                    try {
                        FileOutputStream fos = mContext.openFileOutput(currentSubReddit + FILE_TYPE, Context.MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(saveRedditPost);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
