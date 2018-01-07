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

//        @GET("/api/followuser/{id}")
//        Call<User> followUser(@Path("id") String id);

        @POST("/api/followuser/{id}")
        Call<User> followUser(@Path("id") String id, @Body String memberId);

        @GET("/api/users/{id}/tweets")
        Call<List<Tweet>> getUserTweets(@Path("id") String id);

        @GET("/api/users/{id}/pipers")
        Call<List<User>> getUserFollowing(@Path("id") String id);

        @POST("/api/users")
        Call<User> createUser(@Body User User);

        @GET("/api/tweets")
        Call<List<Tweet>> getAllTweets();

        @POST("/api/tweets")
        Call<Tweet> createTweet(@Body Tweet tweet);

        @DELETE("/api/tweets/{id}")
        Call<Tweet> deleteTweet();

        @POST("/api/users/authenticate")
        Call<Token> authenticate(@Body User user);
    }
