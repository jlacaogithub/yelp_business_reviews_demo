package com.demo.googlemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is for the emotion of the customer
 * @author Joseph
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Emotions {

    @JsonProperty("joyLikelihood")
    private String joyLikelihood;
    @JsonProperty("sorrowLikelihood")
    private String sorrowLikelihood;
    @JsonProperty("angerLikelihood")
    private String angerLikelihood;
    @JsonProperty("surpriseLikelihood")
    private String surpriseLikelihood;

    public Emotions() {
    }

    public Emotions(String joyLikelihood, String sorrowLikelihood, String angerLikelihood, String surpriseLikelihood) {
       
    	this.joyLikelihood = joyLikelihood;
        this.sorrowLikelihood = sorrowLikelihood;
        this.angerLikelihood = angerLikelihood;
        this.surpriseLikelihood = surpriseLikelihood;
    }

    public String getJoyLikelihood() {
        return joyLikelihood;
    }

    public void setJoyLikelihood(String joyLikelihood) {
        this.joyLikelihood = joyLikelihood;
    }

    public String getSorrowLikelihood() {
        return sorrowLikelihood;
    }

    public void setSorrowLikelihood(String sorrowLikelihood) {
        this.sorrowLikelihood = sorrowLikelihood;
    }

    public String getAngerLikelihood() {
        return angerLikelihood;
    }

    public void setAngerLikelihood(String angerLikelihood) {
        this.angerLikelihood = angerLikelihood;
    }

    public String getSurpriseLikelihood() {
        return surpriseLikelihood;
    }

    public void setSurpriseLikelihood(String surpriseLikelihood) {
        this.surpriseLikelihood = surpriseLikelihood;
    }

	@Override
	public String toString() {
		return "Emotions [joyLikelihood=" + joyLikelihood + ", sorrowLikelihood=" + sorrowLikelihood
				+ ", angerLikelihood=" + angerLikelihood + ", surpriseLikelihood=" + surpriseLikelihood + "]";
	}
    
    
}
