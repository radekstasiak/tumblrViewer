package com.example.tumblrviewer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @SerializedName("blog_name")
    public String blogName;
    public String id;
    public String[] tags;

    //@SerializedName("image_permalink")

    public Photo[] photos;




}
