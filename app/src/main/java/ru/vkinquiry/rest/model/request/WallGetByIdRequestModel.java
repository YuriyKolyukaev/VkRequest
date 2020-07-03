package ru.vkinquiry.rest.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

import ru.vkinquiry.consts.ApiConstants;

public class WallGetByIdRequestModel extends BaseRequestModel{

    @SerializedName(ApiConstants.POSTS)
    private String posts;

    @SerializedName(ApiConstants.EXTENDED)
    private int extended = 1;

    public WallGetByIdRequestModel(int ownerId, int postId) {
        this.posts = ownerId + "_" + postId;
    }

    public String getPosts() {
        return posts;
    }

    public int getExtended() {
        return extended;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public void setExtended(int extended) {
        this.extended = extended;
    }

    @Override
    public void onMapCreate(Map<String, String> map) {
        map.put(ApiConstants.POSTS, getPosts());
        map.put(ApiConstants.EXTENDED, String.valueOf(getExtended()));
    }
}
