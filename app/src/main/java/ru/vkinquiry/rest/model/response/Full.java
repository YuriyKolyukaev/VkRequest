package ru.vkinquiry.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Full<T> {                                                                              // корневой уровень пришедшего ответа, он содержит в себе response
    @SerializedName("response")
    @Expose
    public T response;

}
