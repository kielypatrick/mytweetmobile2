package app.myTweet.activity;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import app.myTweet.helpers.CameraHelper;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;
import app.myTweet.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.myTweet.helpers.CameraHelper.showPhoto;

public class Newsfeed extends AppCompatActivity implements AdapterView.OnItemClickListener, Callback<List<Tweet>>{

    ListView listView;
    public MyTweetApp app;
    public TweetAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        app = (MyTweetApp) getApplication();
        Call<List<Tweet>> call1 = (Call<List<Tweet>>) app.myTweetService.getAllTweets();
        call1.enqueue(this);

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
        Log.v("Tweet", "tweet clicked" + tweet.date.toString());

        Intent intent = new Intent(this, tweetview.class);
        intent.putExtra("TWEET_ID", tweet.date);
        startActivity(intent);

    }



    @Override
    public void onResume()
    {
        super.onResume();
        adapter = new TweetAdapter(this, app.tweets, app.users);

        adapter.notifyDataSetChanged();
        newTweetList();

    }

    @Override
    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
        serviceAvailableMessage();
        app.tweets = response.body();
        Log.v("Newsfeed", "Tweets" + app.tweets.toString());

        listView = (ListView) findViewById(R.id.reportList);
        adapter = new TweetAdapter(this, app.tweets, app.users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);



        adapter.notifyDataSetChanged();
        for (Tweet tweet:app.tweets){
            if (tweet.img != null){
            showPhoto(this, tweet, imageView);

            }
        }

        app.myTweetServiceAvailable = true;
    }

    @Override
    public void onFailure(Call<List<Tweet>> call, Throwable t) {
        Log.v("Newsfeed", t.toString());
        app.myTweetServiceAvailable = false;
        serviceUnavailableMessage();
    }
    void serviceUnavailableMessage()
    {
        Toast toast = Toast.makeText(this, "MyTweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
    }

    void serviceAvailableMessage()
    {
        Toast toast = Toast.makeText(this, "MyTweet Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }

    public void newTweetList(){
        for (Tweet tweet:app.tweets){
            //if (!(adapter.tweets.contains(tweet))){
                Call<Tweet> call = (Call<Tweet>) app.myTweetService.deleteTweet();
                call.enqueue(new Callback<Tweet>(){

                    @Override
                    public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                        Log.v("Newsfeed", "Tweets updated" + app.tweets.toString());
                    }

                    @Override
                    public void onFailure(Call<Tweet> call, Throwable t) {
                        Log.v("Newsfeed", "Tweets not updated" + app.tweets.toString());
                    }
                });
          //  }
        }
    }
}

class TweetAdapter extends ArrayAdapter<Tweet>{
    private Context context;
    public List<Tweet> tweets;
    public List <User> users;


    public TweetAdapter(Context context, List<Tweet> tweets, List <User> users) {
        super(context, R.layout.row_layout, tweets);
        this.context = context;
        this.tweets = tweets;
        this.users = users;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.row_layout, parent, false);


        final Tweet tweet = tweets.get(position);
      //  Show newest tweet first;

        Collections.sort(tweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                if (o1.date<(o2.date)){//less than operator used as date in long format
                    return 1;
                }
                else if (o1.date>(o2.date)){
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });
        final TextView author = (TextView) view.findViewById(R.id.row_author);
        for(User user:users) {
            if (tweet.author != null) {
                if (user._id.equals(tweet.author.toString())) {
                    author.setText(user.firstName + " " + user.lastName);
                }
            }
                else
                    author.setText("No author found");
        }

        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MemberTimeline.class);
                intent.putExtra("USER_ID", tweet.author);

                context.startActivity(intent);

                //use context qualifier, suggested by IDE
                Toast toast = Toast.makeText(getContext(), "View Timeline for " + author.getText(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        Log.v("Newsfeed", "your face " + tweet.author.toString() + "  " + tweet.body.toString());

        TextView post = (TextView) view.findViewById(R.id.row_post);

        TextView methodView = (TextView) view.findViewById(R.id.row_method);

            ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
            //ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        if (tweet.img != null){
//            showPhoto(getActivity(), tweet, imageView);
//
//        }
            imgDelete.setTag(tweet);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tweets.remove(tweet); // remove from our list
                    notifyDataSetChanged();

//                Call<Tweet> call = (Call<Tweet>) app.myTweetService.deleteTweet(tweet);
//                call.enqueue(new Callback<Tweet>(){
//
//                    @Override
//                    public void onResponse(Call<Tweet> call, Response<Tweet> response) {
//                    }
//
//                    @Override
//                    public void onFailure(Call<Tweet> call, Throwable t) {
//
//                    }
//                });

                }
            });


        String postAge = "";

        Calendar cal = Calendar.getInstance();

        Long date = tweet.date;
        Date date2 = cal.getTime();
        long elapsedTime = date2.getTime() - date;


        if (elapsedTime <120000) {
            postAge =("Just Now");
        }
        else if (elapsedTime >= 120000 && elapsedTime < 3600000) {
            postAge =((((date2.getTime() - date) / 60000)) + " minutes ago");
        }
        else if (elapsedTime >= 3600000 && elapsedTime < 7200000) {
            postAge =("an hour ago");
        }
        else if (elapsedTime >= 7200000 && elapsedTime < 86400000) {
            postAge =((((date2.getTime() - date) / 3600000)) + " hours ago");
        }
        else if (elapsedTime >= 86400000 && elapsedTime < 172800000) {
            postAge =("yesterday");
        }
        else if (elapsedTime >= 172800000 && elapsedTime < 1100800000) {
            postAge =((((date2.getTime() - date) / 86400000)) + " days ago");
        }
        else if (elapsedTime >= 1100800000) {
            postAge =((((date2.getTime() - date) / 604800000)) + " weeks ago");
        }

        if (tweet.body.length() > 31) {
            post.setText(tweet.body.subSequence(0,30) + "...");

        }
        else {
            post.setText("" + tweet.body);
        }
        methodView.setText("" + postAge);



        return view;

    }


}