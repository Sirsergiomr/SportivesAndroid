package com.example.sportivesandroid.Models;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.sportivesandroid.Utils.Tags;
/**
 * User model {@link User}
 *
 * */
public class User {

    private String username;
    private String name;
    private String lastname;
    private String email;
    private String description;
    private String phone;
    private String photo;
    private String ranking;
    private String team;
    private String type;
    private int balance;
    private int score;
    public User() {
        this.username = "";
        this.name = "";
        this.lastname = "";
        this.email = "";
        this.description = "";
        this.phone = "";
        this.photo = "";
        this.ranking = "";
        this.team = "";
        this.type = "";
        this.balance = 0;
        this.score = 0;
    }

    public User(JSONObject json){
        try {
            this.username = json.getString(Tags.USERNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.name = json.getString(Tags.NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.lastname = json.getString(Tags.LASTNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.email = json.getString(Tags.EMAIL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.description = json.getString(Tags.DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.phone = json.getString(Tags.PHONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.photo = json.getString(Tags.PHOTO);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getUsername() {
        return username;
    }

    public String getName() {return name; }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {return email;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto() {
        return Tags.MEDIA +photo;
    }

    public String getRanking() {
        return ranking;
    }
}
