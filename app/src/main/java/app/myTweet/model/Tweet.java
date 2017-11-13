package app.myTweet.model;


import android.content.Context;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Patrick on 27/09/2017.
 */

public class Tweet {

    public String tweet;
    public Long date;
    public String contact;
    public String author;

    //JSON names for instance fields
    private final String JSON_TWEET   = "tweet"   ;
    private static final String JSON_DATE    = "date"          ;
    private static final String JSON_AUTHOR    = "author"          ;

    public Tweet (EditText tweet)
    {
        this.tweet = tweet.getText().toString();
        this.date = new Date().getTime();
        contact = ":none presently";

    }

    //Constructor for loading JSON objects
    public Tweet(JSONObject json) throws JSONException
    {

        tweet   = json.getString(JSON_TWEET);
        date    = json.getLong(JSON_DATE);
        author  = json.getString(JSON_AUTHOR);

    }

    //Saves model as JSON object
    public JSONObject toJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        json.put(JSON_TWEET   , tweet);
        json.put(JSON_DATE          , date);
        json.put(JSON_AUTHOR    ,author);
        return json;
    }

    }
