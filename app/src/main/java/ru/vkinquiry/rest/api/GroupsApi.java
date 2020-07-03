package ru.vkinquiry.rest.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ru.vkinquiry.model.Group;
import ru.vkinquiry.model.Member;
import ru.vkinquiry.rest.model.response.BaseItemResponse;
import ru.vkinquiry.rest.model.response.Full;

public interface GroupsApi {
    @GET(ApiMethods.GROUPS_GET_MEMBERS)
    Observable<Full<BaseItemResponse<Member>>> getMembers(@QueryMap Map<String, String> map);

    @GET(ApiMethods.GROUPS_GET_BY_ID)
    Observable<Full<List<Group>>> getById(@QueryMap Map<String, String> map);
}
