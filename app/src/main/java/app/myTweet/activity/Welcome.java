package app.myTweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.patrick.mytweet.R;
import app.myTweet.activity.Login;
import app.myTweet.activity.Signup;


/**
 * Created by Patrick on 22/09/2017.
 */

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


    }

    public void loginPressed(View view) {

        startActivity(new Intent(this, Login.class));
    }

    public void signupPressed(View view) {
        startActivity(new Intent(this, Signup.class));
    }

    }
