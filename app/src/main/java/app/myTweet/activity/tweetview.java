package app.myTweet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.patrick.mytweet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;

import static app.myTweet.helpers.IntentHelper.navigateUp;

public class tweetview extends AppCompatActivity {

    private EditText tweetBody;
    private Tweet tweet;
    private TextView date;
    private Button email;
    private Button selectContact;
    public List <Tweet> tweets    = new ArrayList<Tweet>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweetview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.v("Tweet", "Tweetview Started");



        MyTweetApp app = (MyTweetApp) getApplication();
        tweets = app.tweets;

        Date tweetId = (Date)getIntent().getExtras().getSerializable("TWEET_ID");
        Log.v("Tweet", "Tweetview Started" + tweetId);
        tweet = app.getTweet(tweetId);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy ' at ' hh:mm ");

        String displayDate = sdf.format(tweetId);




        tweetBody = (EditText) findViewById(R.id.tweet_body);

        date = (TextView) findViewById(R.id.tweet_date);

        date.setText(displayDate);
        tweetBody.setText(tweet.tweet.getText().toString());





      //  Long tweetId = (Long) getIntent().getExtras().getSerializable("TWEET_ID");

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:  navigateUp(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
