package app.myTweet.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import app.myTweet.main.MyTweetApp;

import static android.R.attr.id;
import static app.myTweet.helpers.LogHelpers.info;

public class Portfolio
{
    public ArrayList<Tweet> tweets;
    public ArrayList<User> users;

    private PortfolioSerializer   serializer;

    public Portfolio(PortfolioSerializer serializer)
    {
        this.serializer = serializer;
        try
        {
            tweets = serializer.loadTweets();

            Log.v("Tweet", "MyTweet App tweets loaded");

        }
        catch (Exception e)
        {
            info(this, "Error loading tweets: " + e.getMessage());
            tweets = new ArrayList<Tweet>();
        }
        try
        {
            users = serializer.loadUsers();
        }
        catch (Exception e)
        {
            info(this, "Error loading users: " + e.getMessage());
            users = new ArrayList<User>();
        }
    }

//    public void newTweet(Tweet tweet) {
//        tweets.add(tweet);
//    }

    public void newUser(User user) {
        users.add(user);
        Log.v("Tweet", "User " + user.toString());

    }

    public Tweet getTweet(Long date) {
        Log.i(this.getClass().getSimpleName(), "Long parameter id: " + date);

        for (Tweet tweet : tweets) {
            if (date.equals(tweet.date)) {
                return tweet;
            }
        }
        return null;
    }

//    public User getUser(Long id) {
//        Log.i(this.getClass().getSimpleName(), "Long parameter id: " + id);
//
//        for (User user : users) {
//            if (id.equals(res.id)) {
//                return user;
//            }
//        }
//        return null;
//    }


    public boolean saveTweets()
    {
        try
        {
            serializer.saveTweets(tweets);
            info(this, "Tweets saved to file");
            Log.v("Tweet", "MyTweet App tweets saverooooneed");

            return true;
        }
        catch (Exception e)
        {
            info(this, "Error saving tweets: " + e.getMessage());
            return false;
        }
    }


    public boolean saveUser()
    {
        try
        {
            serializer.saveUsers(users);
            info(this, "Users saved to file");
            return true;
        }
        catch (Exception e)
        {
            info(this, "Error saving users: " + e.getMessage());
            return false;
        }
    }

}