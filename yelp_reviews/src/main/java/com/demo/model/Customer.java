package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Customer info
 * @author Joseph
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("profile_url")
    private String profileUrl;
    
    @JsonProperty("image_url")
    private String imageUrl;
    
    @JsonProperty("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "Customer [id=" + id + ", profileUrl=" + profileUrl + ", imageUrl=" + imageUrl + ", name=" + name + "]";
	}
    
    
}
