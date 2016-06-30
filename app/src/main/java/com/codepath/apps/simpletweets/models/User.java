package com.codepath.apps.simpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbakhiet on 6/27/16.
 */
public class User implements Parcelable {

    //list attributes
    private String name;
    private long uid;
    private String screenName;
    private String profilePicUrl;
    private String tagline;
    private int followers;
    private int following;

    public int getFollowing() {
        return following;
    }

    public int getFollowers() {
        return followers;
    }

    public String getTagline() {
        return tagline;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    //deserialize json -> User
    public static User fromJSON(JSONObject json){
        User u = new User();
        //extract and fill object from json
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profilePicUrl = json.getString("profile_image_url");
            u.tagline = json.getString("description");
            u.followers = json.getInt("followers_count");
            u.following = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profilePicUrl);
        dest.writeString(this.tagline);
        dest.writeInt(this.followers);
        dest.writeInt(this.following);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readLong();
        this.screenName = in.readString();
        this.profilePicUrl = in.readString();
        this.tagline = in.readString();
        this.followers = in.readInt();
        this.following = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
