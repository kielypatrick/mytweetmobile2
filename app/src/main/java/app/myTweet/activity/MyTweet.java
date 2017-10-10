package app.myTweet.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;



public class MyTweet extends AppCompatActivity implements TextWatcher{

    private MyTweetApp app;
    private TextView length;
    private EditText tweet;

    private int          target = 140;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);
        tweet = (EditText)     findViewById(R.id.newTweet);
        length = (TextView)     findViewById(R.id.wordCount);
        tweet.addTextChangedListener(this);
        app = (MyTweetApp) getApplication();
        progressBar   = (ProgressBar)  findViewById(R.id.progressBar);
        progressBar.setMax(target);
        if (tweet.length() >140) {
            Toast.makeText(this, "TMI honey", Toast.LENGTH_SHORT).show();

        }


        }

    public void submitButtonPressed (View view) {

        app.newTweet(new Tweet(tweet));


        startActivity (new Intent(this, Newsfeed.class));
        Log.v("Tweet", "MyTweet Started " + tweet.getText() + length.getText());
        Toast.makeText(this, "Tweet Sent!", Toast.LENGTH_SHORT).show();


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
}
