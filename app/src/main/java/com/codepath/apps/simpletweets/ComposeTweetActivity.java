package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeTweetActivity extends AppCompatActivity{

    String text;
    private ImageView ivProfPic;
    private ImageView ivExit;
    private EditText etTweet;
    private TextView tvChars;

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
        tvChars = (TextView) findViewById(R.id.tvChars);

        if (getIntent().getStringExtra("username") != null){
            String name = getIntent().getStringExtra("username");
            etTweet.setText("@" + name + " ");
        }

        etTweet.setSelection(etTweet.getText().length());

//        ivProfPic.setImageResource();


        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                tvChars.setText(140 - s.toString().length() + "");
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.activity_compose_tweet, container);
        return view;
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

    public void onExit(View view) {
        finish();
    }
}
