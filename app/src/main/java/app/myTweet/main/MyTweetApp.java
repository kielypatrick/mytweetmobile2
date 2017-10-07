package app.myTweet.main;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.myTweet.model.Tweet;

public class MyTweetApp extends Application
{
    public final int       target       = 10000;
    public int             totalDonated = 0;
    public List <Tweet> tweets    = new ArrayList<Tweet>();
   // public List <User> users = new ArrayList<User>();


    public void newTweet(Tweet tweet)
    {
            tweets.add(tweet);
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Tweet", "MyTweet App Started");
    }
}