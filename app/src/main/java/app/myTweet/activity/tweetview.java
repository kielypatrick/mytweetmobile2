package app.myTweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.patrick.mytweet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;
import app.myTweet.model.User;

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
    public List <User> users    = new ArrayList<User>();

    private static final int REQUEST_CONTACT = 1;
    private Button   sendTweet;
    private MyTweetApp app;
    private String    emailAddress = "";
    private String    emailRetweet = "";



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
        tweets = app.tweets;
        users = app.users;
        Long tweetId = (Long) getIntent().getExtras().getSerializable("TWEET_ID");

        tweet = app.getTweet(tweetId);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy ' at ' hh:mm ");

        String displayDate = sdf.format(tweetId);

        tweetBody = (TextView) findViewById(R.id.tweet_body);

        date = (TextView) findViewById(R.id.tweet_date);
        TextView author = (TextView) findViewById(R.id.tweet_author);
        Log.v("Tweet", "Tweetview Started.........." + tweetId);

        for(User user:users) {

            if (user._id.equals(tweet.author)) {
                author.setText(user.firstName + " " + user.lastName);
                break;
            }
            else
                author.setText("No author found");
        }
        date.setText(displayDate);
        tweetBody.setText(tweet.body);





      //  Long tweetId = (Long) getIntent().getExtras().getSerializable("TWEET_ID");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newsfeed, menu);
        return super.onCreateOptionsMenu(menu);
    }
//    @Override
//    public boolean onNavigateUp()
//    {
//        onBackPressed();
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:  finish();
                //navigateUp(Newsfeed.);
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
                MyTweetApp app = (MyTweetApp) getApplication();

                for(User user:users) {

                    if (user._id.equals(tweet.author)) {
                        emailRetweet = user.firstName + " " + user.lastName;
                        break;
                    } else {
                        emailRetweet = "Somebody";
                    }
                }

                if (app.currentUser._id.equals(tweet.author)){
                    sendEmail(this, emailAddress,
                            "Check out my Tweet!!",tweetBody.getText().toString());
                    }
                else{
                    sendEmail(this, emailAddress,
                            getString(R.string.send_tweet), emailRetweet + " says: " + tweetBody.getText().toString());
                }
                
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