package app.myTweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.patrick.mytweet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;

import static app.myTweet.helpers.ContactHelper.getContact;
import static app.myTweet.helpers.ContactHelper.getEmail;
import static app.myTweet.helpers.ContactHelper.sendEmail;
import static app.myTweet.helpers.IntentHelper.navigateUp;
import static app.myTweet.helpers.IntentHelper.selectContact;

public class tweetview extends AppCompatActivity implements View.OnClickListener{

    private TextView tweetBody;
    private Tweet tweet;
    private TextView date;
    private Button email;
    private Button selectContact;
    public List <Tweet> tweets    = new ArrayList<Tweet>();
    private static final int REQUEST_CONTACT = 1;
    private Button   sendTweet;

    private String    emailAddress = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweetview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.v("Tweet", "Tweetview Started");

        selectContact = (Button)   findViewById(R.id.contact);
        selectContact.setOnClickListener(this);

        email = (Button)   findViewById(R.id.send_tweet);
        email.setOnClickListener(this);




        MyTweetApp app = (MyTweetApp) getApplication();
        tweets = app.portfolio.tweets;
        Long tweetId = (Long) getIntent().getExtras().getSerializable("TWEET_ID");
        Log.v("Tweet", "Tweetview Started" + tweetId);
        tweet = app.portfolio.getTweet(tweetId);




        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy ' at ' hh:mm ");

        String displayDate = sdf.format(tweetId);




        tweetBody = (TextView) findViewById(R.id.tweet_body);

        date = (TextView) findViewById(R.id.tweet_date);

        date.setText(displayDate);
        tweetBody.setText(tweet.tweet.toString());





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

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {


            case R.id.contact : selectContact(this, REQUEST_CONTACT);
                Log.v("Tweet " , "select contact");


                break;
            case R.id.send_tweet :
                sendEmail(this, emailAddress,
                        getString(R.string.send_tweet),tweetBody.getText().toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_CONTACT:
                String name = getContact(this, data);
                emailAddress = getEmail(this, data);
                selectContact.setText(name + " : " + emailAddress);
                Log.v("Tweet " ,name + emailAddress);


                break;
        }
    }
}



