package ru.vkinquiry.rest.model.response;

import java.util.ArrayList;
import java.util.List;

public class BaseItemResponse<T> {                                                                  // содержит в себе count, items, profiles, groups
    public Integer count;
    public List<T> items = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }
}
