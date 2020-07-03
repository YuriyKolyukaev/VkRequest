package ru.vkinquiry.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ru.vkinquiry.model.Topic;
import ru.vkinquiry.rest.model.response.BaseItemResponse;
import ru.vkinquiry.rest.model.response.Full;

public interface BoardApi {
    @GET(ApiMethods.BOARD_GET_TOPICS)
    Observable<Full<BaseItemResponse<Topic>>> getTopics(@QueryMap Map<String, String> map);
}