package app.myTweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by Patrick on 22/09/2017.
 */

public class Signup extends AppCompatActivity implements Callback<User> {

    private MyTweetApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Log.v("Tweet", "Signup page");

    }

    public void registerPressed(View view)
    {
        TextView firstName = (TextView)  findViewById(R.id.firstName);
        TextView lastName  = (TextView)  findViewById(R.id.lastName);
        TextView email     = (TextView)  findViewById(R.id.loginEmail);
        TextView password  = (TextView)  findViewById(R.id.Password);

        User user = new User (firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());

        MyTweetApp app = (MyTweetApp) getApplication();
        Call<User> call = (Call<User>) app.myTweetService.createUser(user);
        call.enqueue(this);

        Log.v("Tweet", "Signup " + email.getText());


        startActivity(new Intent(this, Welcome.class));
    }


//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        userlist.saveUser();
//        Log.v("Tweet", "User saved " );
//
//    }

    @Override
    public void onResponse(Call<User> call, Response<User> response)
    {
      //  app.users.add(response.body());
        startActivity(new Intent(this, Welcome.class));
    }

    @Override
    public void onFailure(Call<User> call, Throwable t)
    {
        app.myTweetServiceAvailable = false;
        Toast toast = Toast.makeText(this, "Donation Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
        startActivity (new Intent(this, Welcome.class));
    }
}

