package app.myTweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import static app.myTweet.helpers.IntentHelper.selectContact;
import static app.myTweet.helpers.ContactHelper.getContact;
import static app.myTweet.helpers.ContactHelper.getEmail;
import static app.myTweet.helpers.ContactHelper.sendEmail;

import android.content.Intent;


import java.util.Date;
import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Portfolio;
import app.myTweet.model.Tweet;


public class MyTweet extends AppCompatActivity implements TextWatcher,View.OnClickListener{

    private TextView length;
    private EditText tweetBody;
    private MyTweetApp app;
    private Tweet tweet;
    private Portfolio portfolio;
    private int          target = 140;
    private ProgressBar progressBar;
    private static final int REQUEST_CONTACT = 1;
    private Button contactButton;
    private Button   sendTweet;

    private String    emailAddress = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);
        tweetBody = (EditText) findViewById(R.id.newTweet);
        tweet = new Tweet(tweetBody);
        length = (TextView) findViewById(R.id.wordCount);
        tweetBody.addTextChangedListener(this);
        app = (MyTweetApp) getApplication();
        portfolio = app.portfolio;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(target);
        contactButton = (Button)   findViewById(R.id.contact);
        contactButton.setOnClickListener(this);
        sendTweet = (Button)   findViewById(R.id.send_tweet);
        sendTweet.setOnClickListener(this);

//        Date tweetId = (Date)getIntent().getExtras().getSerializable("TWEET_ID");
//        tweet = portfolio.getTweet(tweetId);

    }

    @Override
    public void onPause()
    {
        super.onPause();
        portfolio.saveTweets();
        Log.v("Tweet", "MyTweet App Tweets saved");


    }

    private static boolean hasLetters(String tweet) {
        return tweet.matches(".*[a-zA-z].*");

    }

    public void submitButtonPressed (View view) {
        Editable tweetbody = tweetBody.getText();
        Log.v("Tweet " , tweetbody.toString());



        if (hasLetters(tweetbody.toString()) == true) {
            app.newTweet(new Tweet(tweetBody));


            startActivity(new Intent(this, Newsfeed.class));
            Log.v("Tweet", "MyTweet Started " + tweetBody.getText() + length.getText());
            Toast.makeText(this, "Tweet Sent!", Toast.LENGTH_SHORT).show();


        }

        else {
            Toast.makeText(this, "Cat got your tongue??", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuNewsFeed:
                startActivity (new Intent(this, Newsfeed.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLogout:
                startActivity(new Intent(this, Welcome.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mytweet, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String currentText = s.toString();
        int currentLength = currentText.length();
        length.setText("Characters Remaining: " + (140 - currentLength));
        progressBar.setProgress(currentLength);

        if (currentLength >= 140) {
            Toast.makeText(this, "TMI honey", Toast.LENGTH_SHORT).show();

            currentText = currentText.substring(0,139);
        }
    }




    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {


            case R.id.contact : selectContact(this, REQUEST_CONTACT);
//                Log.v("Tweet " , tweetBody.getText().toString());


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
                contactButton.setText(name + " : " + emailAddress);
                Log.v("Tweet " ,name + emailAddress);


                break;
        }
    }
}
