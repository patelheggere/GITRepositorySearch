package com.patelheggere.repositorysearch.utils;

import com.patelheggere.repositorysearch.models.ItemsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patel Heggere on 1/18/2018.
 */

public class Helper {
    public static List<ItemsModel> toList(JSONArray array) throws JSONException {
        List<ItemsModel> list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add((ItemsModel) fromJson(array.get(i)));
        }
        return list;
    }

    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }
}
