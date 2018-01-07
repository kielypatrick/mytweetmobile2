package app.myTweet.activity;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import java.util.Date;

import static app.myTweet.helpers.CameraHelper.showPhoto;
import static app.myTweet.helpers.IntentHelper.selectContact;
import static app.myTweet.helpers.ContactHelper.getContact;
import static app.myTweet.helpers.ContactHelper.getEmail;
import static app.myTweet.helpers.ContactHelper.sendEmail;


import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;
import app.myTweet.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyTweet extends AppCompatActivity implements TextWatcher,View.OnClickListener, Callback<Tweet> {

    private TextView length;
    private EditText tweetBody;
    private MyTweetApp app;
    private Tweet tweet;
    private int          target = 140;
    private ProgressBar progressBar;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 0;

    private Button contactButton;
    private Button   sendTweet;

    private String    emailAddress = "";
    private ImageView cameraButton;
    private ImageView photoView;
    private Activity activity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);


        tweetBody = (EditText) findViewById(R.id.newTweet);
        length = (TextView) findViewById(R.id.wordCount);
        tweetBody.addTextChangedListener(this);
        app = (MyTweetApp) getApplication();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(target);
        contactButton = (Button)   findViewById(R.id.contact);
        contactButton.setOnClickListener(this);
        sendTweet = (Button)   findViewById(R.id.send_tweet);
        sendTweet.setOnClickListener(this);
        cameraButton  = (ImageView) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(this);

        photoView     = (ImageView) findViewById(R.id.myrent_imageView);
//        Date tweetId = (Date)getIntent().getExtras().getSerializable("TWEET_ID");
//        tweet = tweetlist.getTweet(tweetId);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.v("Tweet", "MyTweet App Tweets saved");

    }

    private static boolean hasLetters(String tweet) {
        return tweet.matches(".*[a-zA-z].*");

    }

    public void submitButtonPressed (View view) {
        Editable tweetbody = tweetBody.getText();
        tweet = new Tweet(tweetBody);

        Log.v("Tweet ", "MyTweet " + tweetbody.toString());



        if (hasLetters(tweetbody.toString()) == true) {
            Log.v("MyTweet", tweetBody.getText() + " stored for " + app.currentUser);
            Tweet tweet = new Tweet(tweetBody);
            tweet.author = app.currentUser._id;

            Call<Tweet> call = (Call<Tweet>) app.myTweetService.createTweet(tweet);
            Log.v("MyTweet", tweet.toString() + " stored for " + tweet.author);

            call.enqueue(this);



            startActivity(new Intent(this, Newsfeed.class));
            Log.v("MyTweet", "MyTweet Newsfeed Started " + tweetBody.getText() + " " + length.getText());
            Toast.makeText(this, "Tweet Sent!", Toast.LENGTH_SHORT).show();


        }

        else {
            Toast.makeText(this, "Cat got your thumb??", Toast.LENGTH_SHORT).show();
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

                break;
            case R.id.send_tweet :
                sendEmail(this, emailAddress,
                getString(R.string.send_tweet),tweetBody.getText().toString());

                break;
            case R.id.camera_button:
                Intent ic = new Intent(this, MyTweetCameraActivity.class);
                startActivityForResult(ic, REQUEST_PHOTO);
//                startActivity (new Intent(this, MyTweetCameraActivity.class));
                break;
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

            case REQUEST_PHOTO:
                String filename = data.getStringExtra(MyTweetCameraActivity.EXTRA_PHOTO_FILENAME);
                if (filename != null)
                {
                    tweet = new Tweet(tweetBody);

                    tweet.img = filename;
                    showPhoto(activity, tweet, photoView );
                }
                break;
        }
    }

    @Override
    public void onResponse(Call<Tweet> call, Response<Tweet> response)
    {
        app.tweets.add(response.body());

        Toast toast = Toast.makeText(this, "Tweet Accepted", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onFailure(Call<Tweet> call, Throwable t)
    {
        Toast toast = Toast.makeText(this, "Error making tweet", Toast.LENGTH_LONG);
        toast.show();
    }
}
