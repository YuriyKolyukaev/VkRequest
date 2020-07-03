package ru.vkinquiry.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.VKApiConst;

import java.util.HashMap;
import java.util.Map;

import ru.vkinquiry.CurrentUser;
import ru.vkinquiry.consts.ApiConstants;

public abstract class BaseRequestModel {

    @SerializedName(VKApiConst.VERSION)                                                             // добавляем аннотацию чтобы ретрофит понимал название полей.
            Double version = ApiConstants.DEFAULT_VERSION;

    @SerializedName(VKApiConst.ACCESS_TOKEN)
    String accessToken = CurrentUser.getAccessToken();

    public Double getVersion() {
        return version;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Map<String, String> toMap() {                                                            // метод toMap будет использоваться для преобразования полей класса в массив ключ-значение.
        Map<String, String> map = new HashMap<>();

        map.put(VKApiConst.VERSION, String.valueOf(getVersion()));                                  // т.к getVersion возвращает Double, то преобразуем его методом valueOf в String.
        if (accessToken != null) {
            map.put(VKApiConst.ACCESS_TOKEN, getAccessToken());
        }

        onMapCreate(map);

        return map;
    }

    public abstract void onMapCreate(Map<String, String> map);                                      // для передачи map в дочерние классы

}
