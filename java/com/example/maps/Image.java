package com.example.maps;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("url")
    private String url;

    // Constructor
    public Image(String url) {
        this.url = url;
    }

    // Getter
    public String getUrl() {
        return url;
    }

    // Setter (if needed for deserialization)
    public void setUrl(String url) {
        this.url = url;
    }

}
