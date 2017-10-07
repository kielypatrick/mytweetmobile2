package app.myTweet.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.patrick.mytweet.R;

import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;

public class Newsfeed extends AppCompatActivity {

    ListView listView;
    private MyTweetApp app;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        app = (MyTweetApp) getApplication();


        listView = (ListView) findViewById(R.id.reportList);
        TweetAdapter adapter = new TweetAdapter (this, app.tweets);
        listView.setAdapter(adapter);
        Log.v("Tweet", "Newsfeed Started");
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

        post.setText("" + tweet.tweet.getText());



        return view;

    }
}