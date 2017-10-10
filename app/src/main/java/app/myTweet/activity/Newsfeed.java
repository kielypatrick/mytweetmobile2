package app.myTweet.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;

public class Newsfeed extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    private MyTweetApp app;
    private TweetAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        app = (MyTweetApp) getApplication();


        listView = (ListView) findViewById(R.id.reportList);
        adapter = new TweetAdapter (this, app.tweets);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Log.v("Tweet", "Newsfeed Started");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuMyTweet:
                startActivity(new Intent(this, MyTweet.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLogout:
                startActivity(new Intent(this, Welcome.class));
                break;
            case R.id.menuNewTweet:
                startActivity(new Intent(this, MyTweet.class));

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newsfeed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Tweet tweet = adapter.getItem(position);
        Intent intent = new Intent(this, tweetview.class);
        intent.putExtra("TWEET_ID", tweet.date);
        startActivity(intent);
        //startActivityWithData(this, tweetview.class, "RESIDENCE_ID", tweet.id);

    }
}

class TweetAdapter extends ArrayAdapter<Tweet> {
    private Context context;
    public List<Tweet> tweets;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.row_layout, tweets);
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.row_layout, parent, false);
        Tweet tweet = tweets.get(position);
        TextView post = (TextView) view.findViewById(R.id.row_post);
        TextView methodView = (TextView) view.findViewById(R.id.row_method);
        String postAge = "";

        Calendar cal = Calendar.getInstance();

        Date date = tweet.date;
        Date date2 = cal.getTime();
        long elapsedTime = date2.getTime() - date.getTime();


        if (elapsedTime <60000) {
            postAge =("Just Now");
        }
        else if (elapsedTime >= 60000 && elapsedTime < 3600000) {
            postAge =((((date2.getTime() - date.getTime()) / 60000)) + " minutes ago");
        }
        else if (elapsedTime >= 3600000 && elapsedTime < 7200000) {
            postAge =("an hour ago");
        }
        else if (elapsedTime >= 7200000 && elapsedTime < 86400000) {
            postAge =((((date2.getTime() - date.getTime()) / 3600000)) + " hours ago");
        }
        else if (elapsedTime >= 86400000 && elapsedTime < 172800000) {
            postAge =("yesterday");
        }
        else if (elapsedTime >= 172800000 && elapsedTime < 1100800000) {
            postAge =((((date2.getTime() - date.getTime()) / 86400000)) + " days ago");
        }
        else if (elapsedTime >= 1100800000) {
            postAge =((((date2.getTime() - date.getTime()) / 604800000)) + " weeks ago");
        }


        post.setText("" + tweet.tweet.getText());
        methodView.setText("" + postAge);



        return view;

    }
}