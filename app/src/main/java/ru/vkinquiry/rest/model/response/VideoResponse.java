package ru.vkinquiry.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.vkinquiry.model.attachment.video.Video;

public class VideoResponse {
    public int count;
    @SerializedName("items")
    @Expose
    public List<Video> items;
}
