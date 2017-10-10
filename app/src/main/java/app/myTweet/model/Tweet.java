package app.myTweet.model;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Patrick on 27/09/2017.
 */

public class Tweet {

    public EditText tweet;
    public Date date;
   // public String date1;

    public Tweet (EditText tweet)
    {
        this.tweet = tweet;
        this.date = new Date();

       // SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy ' at ' hh:mm ");

        //this.date1 = sdf.format(date);
    }


}
