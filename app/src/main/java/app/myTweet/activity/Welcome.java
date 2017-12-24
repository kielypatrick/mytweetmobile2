package app.myTweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import java.util.List;

import app.myTweet.activity.Login;
import app.myTweet.activity.Signup;
import app.myTweet.main.MyTweetApp;
import app.myTweet.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Patrick on 22/09/2017.
 */

public class Welcome extends AppCompatActivity implements Callback<List<User>>{

    private MyTweetApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        app = (MyTweetApp) getApplication();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        app.currentUser = null;
        Call<List<User>> call1 = (Call<List<User>>) app.myTweetService.getAllUsers();
        call1.enqueue(this);



    }

    public void loginPressed(View view) {

        startActivity(new Intent(this, Login.class));
    }

    public void signupPressed(View view) {
        startActivity(new Intent(this, Signup.class));
    }


    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response)
    {
        serviceAvailableMessage();
        app.users = response.body();
        app.myTweetServiceAvailable = true;
        Log.v("TweetWelcome", "MyTweet users" + app.users);

    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t)
    {
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
}
