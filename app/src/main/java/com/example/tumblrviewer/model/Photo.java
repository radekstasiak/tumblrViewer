package com.example.tumblrviewer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Radek on 10/12/14.
 */
public class Photo implements Serializable {

    @SerializedName("original_size")
    public PhotoUrl photoUrl;
}
