package app.myTweet.model;

/**
 * Created by Patrick on 29/09/2017.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;


public class Portfolio
{
    public ArrayList<Tweet> tweets;

    public Portfolio() {
        tweets = new ArrayList<Tweet>();
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Tweet getTweet(Date date) {
        Log.i(this.getClass().getSimpleName(), "Long parameter id: " + date);

        for (Tweet tweet : tweets) {
            if (date.equals(tweet.date)) {
                return tweet;
            }
        }
        return null;
    }


}