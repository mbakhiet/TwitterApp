package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeTweetActivity extends AppCompatActivity{

    String text;
    private ImageView ivProfPic;
    private ImageView ivExit;
    private EditText etTweet;

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        ft.replace(R.id.flHolder, new ComposeTweetFragment());
//        ft.commit();
        ivProfPic = (ImageView) findViewById(R.id.ivProfPic);
        ivExit = (ImageView) findViewById(R.id.ivExit);
        ivExit.setImageResource(R.drawable.close);
        etTweet = (EditText) findViewById(R.id.etTweet);
        client = new TwitterClient(this);
    }

    public void onClick(View view) {
            String tweet = etTweet.getText().toString();
            client.composeTweet(tweet, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    Tweet newTweet = Tweet.fromJSON(jsonObject);
                    Intent data = new Intent();
                    // Pass relevant data back as a result
                    data.putExtra("tweet", newTweet);
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });

    }

}
