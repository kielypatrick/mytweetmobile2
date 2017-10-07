package app.myTweet.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.patrick.mytweet.R;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;


public class MyTweet extends AppCompatActivity {

    public int    length;
    private MyTweetApp app;


    private EditText tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);
        tweet = (EditText)     findViewById(R.id.newTweet);
        app = (MyTweetApp) getApplication();

    }

    public void submitButtonPressed (View view) {

        app.newTweet(new Tweet(length, tweet));


        startActivity (new Intent(this, Newsfeed.class));
        Log.v("Tweet", "MyTweet Started " + tweet.getText());

    }


    //}

}
