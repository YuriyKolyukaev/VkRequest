package ru.vkinquiry.model.attachment.doc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import ru.vkinquiry.model.attachment.video.Video;

public class Preview extends RealmObject {

    @SerializedName("photo")
    @Expose
    public PhotoPreview photo;

    @SerializedName("video")
    @Expose
    public Video video;

    public PhotoPreview getPhoto() {
        return photo;
    }

    public Video getVideo() {
        return video;
    }

    public void setPhoto(PhotoPreview photo) {
        this.photo = photo;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
