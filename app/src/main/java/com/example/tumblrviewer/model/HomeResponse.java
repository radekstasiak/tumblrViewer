package com.example.tumblrviewer.model;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

public class HomeResponse implements Serializable {

    @SerializedName("posts")
    public Item[] items;

    public static HomeResponse fromJsonObject(JSONObject jsonObject) {
        Gson gson = new Gson();

        return gson.fromJson(jsonObject.toString(), HomeResponse.class);
    }

}
