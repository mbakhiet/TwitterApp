package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TweetsArrayAdapter;
import com.codepath.apps.simpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbakhiet on 6/27/16.
 */
public class TweetsListFragment extends Fragment{

    private TweetsArrayAdapter aTweets;
    private List<Tweet> tweets;
    private ListView lvTweets;

    public TweetsArrayAdapter getAdapter() {
        return aTweets;
    }

    public ListView getLvTweets() {
        return lvTweets;
    }

    //inflation logic

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        //find listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        //connect adapter to listview
        lvTweets.setAdapter(aTweets);
        return v;
    }

    //creation lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create arraylist
        tweets = new ArrayList<Tweet>();
        //construct adapter
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void add(Tweet tweet){
        aTweets.insert(tweet, 0);
        aTweets.notifyDataSetChanged();
        lvTweets.setSelection(0);
    }
}

