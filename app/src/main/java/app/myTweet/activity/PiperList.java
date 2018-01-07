package app.myTweet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import app.myTweet.main.MyTweetApp;
import app.myTweet.model.Tweet;
import app.myTweet.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.myTweet.helpers.CameraHelper.showPhoto;

/**
 * Created by Patrick on 05/01/2018.
 */

public class PiperList extends AppCompatActivity implements AdapterView.OnItemClickListener, Callback<List<User>> {

  ListView listView;
  public MyTweetApp app;
  public UserAdapter adapter;
  public List <User> users;
  public TextView member;
//    public User user;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_piperlist);

    app = (MyTweetApp) getApplication();

    member = (TextView) findViewById(R.id.member);

    String memberId = (String) getIntent().getExtras().getSerializable("USER_ID");
    for(User user:app.users) {
      if (user._id.equals(memberId)){
        member.setText(user.firstName + " " + user.lastName);
      }
    }
    Call<List<User>> call1 = (Call<List<User>>) app.myTweetService.getUserFollowing(memberId);
    call1.enqueue(this);


    Log.v("Tweet", "MemberTimeline Started");
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
    User user = adapter.getItem(position);
    Log.v("PiperList", "user clicked" + user.email);

    Intent intent = new Intent(this, MemberTimeline.class);
    intent.putExtra("MEMBER_ID", user._id);
    startActivity(intent);

  }

  @Override
  public void onResume()
  {
    super.onResume();
    adapter = new UserAdapter(this, app.users);

    adapter.notifyDataSetChanged();

  }

  @Override
  public void onResponse(Call<List<User>> call, Response<List<User>> response) {
    serviceAvailableMessage();
    app.users = response.body();
    Log.v("PiperList", "Users");

    listView = (ListView) findViewById(R.id.piperList);
    adapter = new UserAdapter(this, app.users);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(this);

    app.myTweetServiceAvailable = true;
  }

  @Override
  public void onFailure(Call<List<User>> call, Throwable t) {
    Log.v("MemberTimeline", t.toString());
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
    Toast toast = Toast.makeText(this, "MyTweet Contacted Successfully", Toast.LENGTH_SHORT);
    toast.show();
  }


  public void followUserPressed(View v) {
    final String memberId = (String) getIntent().getExtras().getSerializable("USER_ID");
    String currentUser = app.currentUser._id;
    Call<User> call = (Call<User>) app.myTweetService.followUser(currentUser, memberId);
    call.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        serviceAvailableMessage();
        for(User user:app.users) {
          if (user._id.equals(memberId)){
            user.followers.add(response.body());
          }
          else {
            Log.v("MemberTimeline", "no user found");

          }

        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        serviceUnavailableMessage();
      }
    });

  }
}

class UserAdapter extends ArrayAdapter<User> {
  private Context context;
  public List <User> users;
  public MyTweetApp app;


  public UserAdapter(Context context, List <User> users) {
    super(context, R.layout.row_piper_layout);
    this.context = context;
    this.users = users;

  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View view = inflater.inflate(R.layout.row_piper_layout, parent, false);

    final User user = users.get(position);
    TextView member = (TextView) view.findViewById(R.id.row_member);
    for(User user1:users) {
      if (user1._id != null) {
        if (user1._id.equals(user._id)) {
          member.setText(user.firstName + " " + user.lastName);
        }
      }
      else
        member.setText("Missing..");
    }

    member.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        context.startActivity(new Intent(getContext(), MemberTimeline.class));

        //getApplicationContext() here, getContext() in the adapter
        Toast toast = Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT);
        toast.show();
      }
    });



    return view;

  }


}
