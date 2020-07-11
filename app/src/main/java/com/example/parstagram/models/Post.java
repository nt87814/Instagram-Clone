package com.example.parstagram.models;

import com.example.parstagram.fragments.DetailsFragment;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
/**
 * Class for Post objects
 * */
@ParseClassName("Post")
//@Parcel
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_NUM_LIKES = "likedBy";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKES = "likes";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() { return getParseUser(KEY_USER); }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public ArrayList<ParseObject> getLikes() { return (ArrayList<ParseObject>) get(KEY_LIKES); }

    public void addLike(ParseUser parseUser) {
        ArrayList<ParseObject> likedBy = getLikes();
        if (likedBy != null && !DetailsFragment.has(likedBy, parseUser)) {
            likedBy.add(parseUser);
        }

        else {
            likedBy = new ArrayList<>();
            likedBy.add(parseUser);
        }
        put(KEY_LIKES, likedBy);
    }

}
