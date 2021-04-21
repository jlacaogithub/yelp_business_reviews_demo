package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This holds the customer reviews on the business
 * @author Joseph
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("rating")
    private String rating;
	
	@JsonProperty("user")
    private Customer customer;
	
	@JsonProperty("text")
    private String text;
	
    @JsonProperty("time_created")
    private String timeCreated;
    
    @JsonProperty("url")
    private String url;
    private Emotions emotions;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Emotions getEmotions() {
		return emotions;
	}
	public void setEmotions(Emotions emotions) {
		this.emotions = emotions;
	}
	@Override
	public String toString() {
		return "Review [id=" + id + ", rating=" + rating + ", customer=" + customer + ", text=" + text
				+ ", timeCreated=" + timeCreated + ", url=" + url + ", emotions=" + emotions + "]";
	}
}
