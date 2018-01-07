package app.myTweet.main;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.myTweet.model.Tweet;
import app.myTweet.model.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyTweetApp extends Application
{
    public MyTweetService myTweetService;
    protected static MyTweetApp app;

    public boolean         myTweetServiceAvailable = false;

    public String          service_url  = "http://10.0.2.2:4000";   // Standard Emulator IP Address


    public List <Tweet> tweets    = new ArrayList<Tweet>();
    public List <User> users = new ArrayList<User>();

    public User             currentUser;


    public void newTweet(Tweet tweet)
    {
      tweets.add(tweet);
        Log.v("Tweet", "MyTweeeeeeeetApp " + tweet.body);
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

    @Override
    public void onCreate()
    {
        super.onCreate();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myTweetService = retrofit.create(MyTweetService.class);

        Log.v("TweetApp", "MyTweet App Started now" + tweets);

        Log.v("TweetApp", "MyTweet users" + users);

    }

    public void newUser (User user)
    {

        //users.add(user);;  tHIS WAS INCLUDED BEFORE PORTFOLIO USE
        users.add(user);

    }


    public boolean validUser (String email)
    {
        for (User user : users)
        {
            if (user.email.equals(email))
            {
                currentUser = user;
                return true;


            }
        }
        return false;
    }

    public MyTweetApp getApp() {
        return app;
    }


}