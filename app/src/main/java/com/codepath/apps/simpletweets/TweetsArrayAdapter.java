package com.codepath.apps.simpletweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mbakhiet on 6/27/16.
 */

//taking tweet objects and turning them into views
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{


    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get tweet
        final Tweet tweet = getItem(position);
        //find and inflate template
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        //find subviews to fill w/ data
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfPic);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvPostDate = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvFullName);

        //populate data into subviews
        tvUserName.setText("@" + tweet.getUser().getScreenName());
        tvScreenName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvPostDate.setText(tweet.getRelTime());

        ivProfileImage.setImageResource(android.R.color.transparent);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = tweet.getUser();
                Intent i = new Intent(getContext(), OtherProfileActivity.class);
                i.putExtra("user", user);
                getContext().startActivity(i);
            }
        });

        Picasso.with(getContext()).load(tweet.getUser().getProfilePicUrl())
                .transform(new RoundedCornersTransformation(3, 3)).into(ivProfileImage);
        //return view to be inserted into list
        return convertView;
    }

}
