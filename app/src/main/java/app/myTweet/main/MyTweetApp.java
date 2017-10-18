package app.myTweet.main;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.myTweet.model.Portfolio;
import app.myTweet.model.Tweet;
import app.myTweet.model.User;

public class MyTweetApp extends Application
{
    public final int       target       = 10000;
    public int             totalDonated = 0;
    public List <Tweet> tweets    = new ArrayList<Tweet>();
    public List <User> users = new ArrayList<User>();


    public void newTweet(Tweet tweet)
    {
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

    public Portfolio portfolio;



    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Tweet", "MyTweet App Started");
    }

    public void newUser (User user)
    {
        users.add(user);
    }


    public boolean validUser (String email, String password)
    {
        for (User user : users)
        {
            if (user.email.equals(email) && user.password.equals(password))
            {
                return true;
            }
        }
        return false;
    }

}