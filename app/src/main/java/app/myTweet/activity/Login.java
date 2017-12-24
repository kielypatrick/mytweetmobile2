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
import app.myTweet.model.Token;
import app.myTweet.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Patrick on 26/09/2017.
 */

public class Login extends AppCompatActivity implements Callback<Token> {

    public MyTweetApp app = (MyTweetApp) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (MyTweetApp) getApplication();

        Log.v("Tweet", "Login page");

    }

    public void login(View view)  {

        TextView email     = (TextView)  findViewById(R.id.loginEmail);
        TextView password  = (TextView)  findViewById(R.id.loginPassword);

        User user = new User ("", "", email.getText().toString(), password.getText().toString());


//        if (app.validUser(email.getText().toString(), password.getText().toString()))
//        {
//            startActivity (new Intent(this, MyTweet.class));
//        }
//        else
//        {
//            Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
//            toast.show();
//        }>>>this method is not used now, so the app.validUser line below is included to populate currentUser
//     >>>>>>>after login

        Call<Token> call = (Call<Token>) app.myTweetService.authenticate(user);
        app.validUser(email.getText().toString());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        startActivity(new Intent(this, Newsfeed.class));

    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {
        app.myTweetServiceAvailable = false;
        Toast toast = Toast.makeText(this, "Donation Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
        startActivity (new Intent(this, Welcome.class));

    }
}
