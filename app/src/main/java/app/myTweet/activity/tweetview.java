package app.myTweet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.patrick.mytweet.R;

import java.util.Date;
import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Portfolio;
import app.myTweet.model.Tweet;

public class tweetview extends AppCompatActivity {

    private EditText tweetBody;
    private Tweet tweet;
    private TextView date;
    private Button email;
    private Button selectContact;
    private Portfolio tweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweetview);
        Log.v("Tweet", "Tweetview Started");

//        tweetBody = (EditText) findViewById(R.id.tweet_body);
//
//        date = (TextView) findViewById(R.id.tweet_date);
//
//        MyTweetApp app = (MyTweetApp) getApplication();
//        tweets = app.portfolio;
//
        Date date1 = (Date)getIntent().getExtras().getSerializable("TWEET_ID");
        Log.v("Tweet", "Tweetview Started" + date1);

//        tweet = tweets.getTweet(date1);


    }






}
