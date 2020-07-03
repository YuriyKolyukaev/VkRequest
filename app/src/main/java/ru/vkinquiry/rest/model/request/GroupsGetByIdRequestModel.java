package ru.vkinquiry.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.VKApiConst;

import java.util.Map;

import ru.vkinquiry.consts.ApiConstants;

public class GroupsGetByIdRequestModel extends BaseRequestModel {

    @SerializedName(VKApiConst.GROUP_ID)
    int groupId;

    @SerializedName(VKApiConst.FIELDS)
    String fields = ApiConstants.DEFAULT_GROUP_FIELDS;


    public GroupsGetByIdRequestModel(int groupId) {
        this.groupId = Math.abs(groupId);
    }

    public int getGroupId() {
        return groupId;
    }

    public String getFields() {
        return fields;
    }

    public void setGroupId(int groupId) {
        this.groupId = Math.abs(groupId);
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    @Override
    public void onMapCreate(Map<String, String> map) {
        map.put(VKApiConst.GROUP_ID, String.valueOf(getGroupId()));
        map.put(VKApiConst.FIELDS, String.valueOf(getFields()));

    }
}
