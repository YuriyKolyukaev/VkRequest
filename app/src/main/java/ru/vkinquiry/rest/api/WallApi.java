package ru.vkinquiry.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ru.vkinquiry.rest.model.response.GetWallByIdResponse;
import ru.vkinquiry.rest.model.response.GetWallResponse;
// формат запроса
public interface WallApi {

    @GET(ApiMethods.WALL_GET)
        // параметры запроса
    Observable<GetWallResponse> get(@QueryMap Map<String, String> map);

    @GET(ApiMethods.WALL_GET_BY_ID)
    Observable<GetWallByIdResponse> getById(@QueryMap Map<String, String> map);
}
