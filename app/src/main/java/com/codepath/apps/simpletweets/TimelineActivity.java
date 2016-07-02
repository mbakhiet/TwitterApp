package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.simpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.simpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.simpletweets.fragments.TweetsListFragment;
import com.codepath.apps.simpletweets.models.Tweet;

public class TimelineActivity extends AppCompatActivity{

    final int REQUEST_CODE = 10;
    TweetsPagerAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Get viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // Set viewpager adapter
        adapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapter);
//        Toast.makeText(TimelineActivity.this, getVisibleFragment().toString(), Toast.LENGTH_SHORT).show();
        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach tabstrip to viewpager
        tabStrip.setViewPager(vpPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void onComposeTweet(MenuItem item) {
        Intent i = new Intent(this, ComposeTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        //pass arbitrary data
        i.putExtra("hi", "chew");
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if ((resultCode == RESULT_OK) && (requestCode == REQUEST_CODE)) {
            Tweet tweet = data.getExtras().getParcelable("tweet");
            TweetsListFragment frag = (TweetsListFragment) adapter.getRegisteredFragment(0);
            frag.add(tweet);
        }
    }

    //return order of fragments in the ViewPager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        //the order and creation of fragments
        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new HomeTimelineFragment();
            }
            else if (position == 1){
                return new MentionsTimelineFragment();
            }
            else {
                return null;
            }
        }

        //returns tabTitle
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //return # of fragments
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
