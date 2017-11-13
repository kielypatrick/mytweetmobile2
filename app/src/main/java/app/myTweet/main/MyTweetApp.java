package app.myTweet.main;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.myTweet.model.Portfolio;
import app.myTweet.model.PortfolioSerializer;
import app.myTweet.model.Tweet;
import app.myTweet.model.User;

import static app.myTweet.helpers.LogHelpers.info;

public class MyTweetApp extends Application
{

    public List <Tweet> tweets    = new ArrayList<Tweet>();
    public List <User> users = new ArrayList<User>();
    private static final String FILENAME = "portfolio.json";
    public Portfolio portfolio;





    public void newTweet(Tweet tweet)
    {
       // tweets.add(tweet);  tHIS WAS INCLUDED BEFORE PORTFOLIO USE. THEN IT CAUSED DUPLICATE NEWSFEED ENTRIES
        portfolio.tweets.add(tweet);
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




    @Override
    public void onCreate()
    {
        super.onCreate();
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(serializer);
        tweets = portfolio.tweets;

        Log.v("Tweet", "MyTweet App Started now" + tweets);

        users = portfolio.users;
        Log.v("Tweet", "MyTweet users" + users);

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