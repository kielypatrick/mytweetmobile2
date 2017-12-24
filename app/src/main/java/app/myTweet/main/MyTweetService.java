package app.myTweet.main;

import java.util.List;

import app.myTweet.model.Token;
import app.myTweet.model.Tweet;
import app.myTweet.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Patrick on 22/11/2017.
 */

public interface MyTweetService

    {
        @GET("/api/users")
        Call<List<User>> getAllUsers();

        @GET("/api/users/{id}")
        Call<User> getUser(@Path("id") String id);

        @POST("/api/users")
        Call<User> createUser(@Body User User);

        @GET("/api/tweets")
        Call<List<Tweet>> getAllTweets();

        @POST("/api/tweets")
        Call<Tweet> createTweet(@Body Tweet tweet);

        @POST("/api/users/authenticate")
        Call<Token> authenticate(@Body User user);
    }
