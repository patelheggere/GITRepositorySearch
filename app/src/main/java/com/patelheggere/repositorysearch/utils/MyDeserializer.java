package com.patelheggere.repositorysearch.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.patelheggere.repositorysearch.models.ItemsModel;
import com.patelheggere.repositorysearch.models.OwnerModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patel Heggere on 1/18/2018.
 */

public class MyDeserializer implements JsonDeserializer<ItemsModel> {

    @Override
    public ItemsModel deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        final Gson gson = new Gson();
        final JsonObject obj = je.getAsJsonObject(); //our original full json string
        final JsonElement dataElement = obj.get("items");
        final JsonElement ownerElement = dataElement.getAsJsonObject().get("owner");
        final JsonArray idArray = dataElement.getAsJsonArray();
        System.out.println("Items:"+dataElement.toString()+"\n owner:"+ownerElement.toString());
        final List<OwnerModel> parsedData = new ArrayList<>();
        for (Object object : idArray) {
            final JsonObject jsonObject = (JsonObject) object;
            //can pass this into constructor of Id or through a setter
            final JsonObject stuff = jsonObject.get("stuff").getAsJsonObject();
            final JsonArray valuesArray = jsonObject.getAsJsonArray("values");
            final OwnerModel id = new OwnerModel();
            for (Object value : valuesArray) {
                final JsonArray nestedArray = (JsonArray) value;
                final OwnerModel ownerModel = gson.fromJson(nestedArray, OwnerModel.class);

            }
            parsedData.add(id);
        }
        return new ItemsModel();
    }
}