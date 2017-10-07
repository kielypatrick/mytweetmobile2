package app.myTweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;


/**
 * Created by Patrick on 26/09/2017.
 */

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view)  {
        //DonationApp app = (DonationApp) getApplication();

        //TextView email     = (TextView)  findViewById(R.id.loginEmail);
        //TextView password  = (TextView)  findViewById(R.id.loginPassword);

       // if (app.validUser(email.getText().toString(), password.getText().toString()))
      //  {
            startActivity (new Intent(this, MyTweet.class));
        //}
        //else
        //{
          //  Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
            //toast.show();
        //}
    }

}
