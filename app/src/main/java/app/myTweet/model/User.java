package app.myTweet.model;

import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Patrick on 26/09/2017.
 */

public class User {
    public String _id;

    public String firstName;
    public String lastName;
    public String email;
    public String password;

    //JSON names for instance fields
    private final String JSON_FIRSTNAME   = "firstName"   ;
    private static final String JSON_LASTNAME    = "lastName"          ;
    private static final String JSON_EMAIL    = "email"          ;
    private static final String JSON_PASSWORD    = "password"          ;



    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //Constructor for loading JSON objects
    public User(JSONObject json) throws JSONException
    {

        firstName   = json.getString(JSON_FIRSTNAME);
        lastName    = json.getString(JSON_LASTNAME);
        email    = json.getString(JSON_EMAIL);
        password    = json.getString(JSON_EMAIL);
    }
    //Saves model as JSON object
    public JSONObject toJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        json.put(JSON_FIRSTNAME   , firstName);
        json.put(JSON_LASTNAME          , lastName);
        json.put(JSON_EMAIL          , email);
        json.put(JSON_EMAIL          , password);

        return json;
    }

}
