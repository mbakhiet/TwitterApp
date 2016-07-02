package com.codepath.apps.simpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.simpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class OtherProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = getIntent().getParcelableExtra("user");
        String screenName = user.getScreenName();
        setContentView(R.layout.activity_other_profile);
        getSupportActionBar().setTitle("@" + user.getScreenName());
        populateProfileHeader(user);
        if (savedInstanceState == null) {
            //create user timeline fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            //dynamically display fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfPic);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());

        DecimalFormat formatter = new DecimalFormat("#,###.##");

        int followers = user.getFollowers();
        int following = user.getFollowing();

        if (followers < 1000000){
            tvFollowers.setText(formatter.format(followers) + " Followers");
        }
        else{
            double mfollowers = followers / 1000000.0;
            tvFollowers.setText(formatter.format(mfollowers) + "m Followers");
        }

        tvFollowing.setText(formatter.format(following) + " Following");


        Picasso.with(this).load(user.getProfilePicUrl()).transform(new RoundedCornersTransformation(3, 3)).into(ivProfileImage);
    }
}
