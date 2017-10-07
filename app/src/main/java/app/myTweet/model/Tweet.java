package app.myTweet.model;

import android.widget.EditText;

/**
 * Created by Patrick on 27/09/2017.
 */

public class Tweet {
    public int    length;

    public EditText tweet;

    public Tweet (int length, EditText tweet)
    {
        this.length = length;
        this.tweet = tweet;
    }


}
