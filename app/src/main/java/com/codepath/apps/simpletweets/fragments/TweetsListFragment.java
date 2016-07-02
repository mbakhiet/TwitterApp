package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class TweetsListFragment extends Fragment implements refresh{

    private TweetsArrayAdapter aTweets;
    private List<Tweet> tweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;

    public TweetsArrayAdapter getAdapter() {
        return aTweets;
    }

    public ListView getLvTweets() {
        return lvTweets;
    }

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

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                getAdapter().clear();
                populateTimeline();
                lvTweets.setSelection(0);
//                Toast.makeText(getActivity(), "suh", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });

        return v;
    }

    public void populateTimeline(){

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

interface refresh{
        public void populateTimeline();
        }

