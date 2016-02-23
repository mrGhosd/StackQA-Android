package com.vsokoltsov.stackqa.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vsokoltsov on 23.02.16.
 */
public class Vote {
    private int id;
    private String voteValue;
    private int userId;
    private int rate;
    private int voteId;
    private String voteType;

    public Vote() {}

    public Vote(JSONObject vote) {
        try {
            if(vote.has("id")) setId(vote.getInt("id"));
            if (vote.has("vote_value")) setVoteValue(vote.getString("vote_value"));
            if (vote.has("user_id")) setUserId(vote.getInt("user_id"));
            if (vote.has("rate")) setRate(vote.getInt("rate"));
            if (vote.has("vote_id")) setVoteId(vote.getInt("vote_id"));
            if (vote.has("vote_type")) setVoteType(vote.getString("vote_type"));

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(String voteValue) {
        this.voteValue = voteValue;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }
}
