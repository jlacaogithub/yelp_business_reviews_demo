package com.demo.googlemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

    @JsonProperty("imageUri")
    private String imageUri;

    public Source(String imageUri) {
        this.imageUri = imageUri;
    }

    public Source() {
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
