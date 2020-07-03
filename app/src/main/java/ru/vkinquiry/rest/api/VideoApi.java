package ru.vkinquiry.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ru.vkinquiry.rest.model.response.Full;
import ru.vkinquiry.rest.model.response.VideoResponse;


public interface VideoApi {
    @GET(ApiMethods.VIDEO_GET)
    Observable<Full<VideoResponse>> get(@QueryMap Map<String, String> map);
}
