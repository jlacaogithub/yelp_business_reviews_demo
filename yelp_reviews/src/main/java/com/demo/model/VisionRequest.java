package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisionRequest {

    @JsonProperty("requests")
    private List<Request> requests;

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public static VisionRequest assemble(String imageUri){
        VisionRequest visionRequest = new VisionRequest();

        Image image = new Image();
        image.setSource(new Source(imageUri));

        Feature feature = new Feature(FeatureType.FACE_DETECTION, 1);
        List<Feature> features = Collections.singletonList(feature);
        Request request = new Request(image, features);


        visionRequest.setRequests(Collections.singletonList(request));

        return visionRequest;
    }
}
