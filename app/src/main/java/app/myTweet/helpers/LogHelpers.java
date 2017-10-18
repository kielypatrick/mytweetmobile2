package app.myTweet.helpers;

/**
 * Created by Patrick on 29/09/2017.
 */

import android.util.Log;

public class LogHelpers
{
    public static void info(Object parent, String message)
    {
        Log.i(parent.getClass().getSimpleName(), message);
    }
}