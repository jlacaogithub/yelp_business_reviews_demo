package com.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.demo.constant.CommonConstant;
import com.demo.googlemodel.Emotions;
import com.demo.googlemodel.VisionRequest;
import com.demo.model.Business;
import com.demo.model.Location;
import com.demo.model.Review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Class that helps the business and reviews service
 * 
 * @author Joseph
 *
 */
@Component
public class BusinessHelper {

	private static final Logger logger = LoggerFactory.getLogger(BusinessHelper.class);

	@Autowired
	RestTemplate restTemplate;

	@Value("${yelp.api.key}")
	private String API_KEY;

	@Value("${google_api.key}")
	private String googleKey;

	/**
	 * Method that will get businesses information based on the filter
	 * 
	 * @param term     {@link String}
	 * @param location {@link String}
	 * @return {@link Business}
	 */
	public List<Business> getBusinesses(String term, String location) {
		String method = "getBusinesses(" + term + "," + location + ")";
		logger.info("in : " + method);
		List<Business> businesses = new ArrayList();
		try {
			URIBuilder builder = new URIBuilder(CommonConstant.YELP_API_URI + CommonConstant.YELP_API_BUSINESS_PATH
					+ CommonConstant.YELP_API_SEARCH_PATH);
			builder.setParameter("term", term).setParameter("location", location);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonBusinesses = objectMapper.readTree(restTemplate
					.exchange(builder.build(), HttpMethod.GET, new HttpEntity<>(generateRequestHeaders()), String.class)
					.getBody()).get("businesses");
			logger.info("Json Node: {}", jsonBusinesses);
			if (jsonBusinesses.isArray()) {
				for (JsonNode jsonBusiness : jsonBusinesses) {
					Business business = populateBusiness(jsonBusiness, objectMapper);
					if (business != null) {
						businesses.add(business);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"getBusinessInformation():::Exception caught while retrieving business information, reason ::: ",
					e);
		}
		logger.info("out : " + method);
		return businesses;
	}

	/**
	 * Method that will get the business information
	 * 
	 * @param id {@link String}
	 * @return {@link Business}
	 */
	public Business getBusinessInformation(String id) {
		String method = "getBusinessInformation(" + id + ")";
		logger.info("in : " + method);
		Business business = null;
		try {
			URIBuilder builder = new URIBuilder(
					CommonConstant.YELP_API_URI + CommonConstant.YELP_API_BUSINESS_PATH + "/" + id);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonBusiness = objectMapper.readTree(restTemplate
					.exchange(builder.build(), HttpMethod.GET, new HttpEntity<>(generateRequestHeaders()), String.class)
					.getBody());

			logger.info("Json Node: {}", jsonBusiness);
			if (!jsonBusiness.isNull()) {
				business = objectMapper.readValue(jsonBusiness.toString(), Business.class);
				if (business != null) {
					business.setLocation(
							objectMapper.readValue(jsonBusiness.get("location").toString(), Location.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"getBusinessInformation():::Exception caught while retrieving business information, reason ::: ",
					e);
		}
		logger.info("out : " + method);
		return business;
	}

	/**
	 * Method that will get the business reviews
	 * 
	 * @param id {@link String}
	 * @return {@link List}<{@link Review}
	 */
	public List<Review> getBusinessReviews(String id) {
		String method = "getBusinessReviews(" + id + ")";
		logger.info("in : " + method);
		List<Review> reviews = new ArrayList<>();
		try {
			URIBuilder builder = new URIBuilder(CommonConstant.YELP_API_URI + CommonConstant.YELP_API_BUSINESS_PATH
					+ "/" + id + CommonConstant.YELP_API_REVIEW_PATH);

			ObjectMapper objectMapper = new ObjectMapper();
			TypeFactory typeFactory = objectMapper.getTypeFactory();
			String reviewsJson = objectMapper
					.readTree(restTemplate.exchange(builder.build(), HttpMethod.GET,
							new HttpEntity<>(generateRequestHeaders()), String.class).getBody())
					.get("reviews").toString();

			reviews = objectMapper.readValue(reviewsJson,
					typeFactory.constructCollectionType(List.class, Review.class));
			// get avatar emotions
			for (Review rev : reviews) {
				populateAvatarEmotions(rev.getCustomer().getImageUrl(), rev);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getBusinessInformation():::Exception caught while retrieving reviews, reason ::: ", e);
		}
		logger.info("out : " + method);
		return reviews;
	}

	/**
	 * Method that will populate/generate request headers
	 * 
	 * @return {@link HttpHeaders}
	 */
	private HttpHeaders generateRequestHeaders() {
		return new HttpHeaders() {
			{
				set(HttpHeaders.AUTHORIZATION, CommonConstant.YELP_API_AUTH_KEY + API_KEY);
				setContentType(MediaType.APPLICATION_JSON);
			}
		};
	}

	/**
	 * Method that will populate Business information from the YELP results
	 * 
	 * @param jsonBusinesses {@link JsonNode}
	 * @param objectMapper   {@link ObjectMapper}
	 * @return {@link Business}
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private Business populateBusiness(JsonNode jsonBusiness, ObjectMapper objectMapper)
			throws JsonMappingException, JsonProcessingException {
		Business business = objectMapper.readValue(jsonBusiness.toString(), Business.class);
		if (business != null) {
			business.setLocation(objectMapper.readValue(jsonBusiness.get("location").toString(), Location.class));
		}
		return business;
	}

	private void populateAvatarEmotions(String imageUrl, Review rev) {
		try {
			Emotions emotions = getReviewerAvatarEmotion(rev.getCustomer().getImageUrl());
			if (emotions != null) {
				rev.setEmotions(emotions);
			}
		} catch (Exception e) {
			// possible error is invalid key
		}
	}

	/**
	 * Method that will detect face vision
	 * 
	 * @param imageUrl {@link String}
	 * @return {@link Emotions}
	 * @throws Exception
	 */
	private Emotions getReviewerAvatarEmotion(String imageUrl) throws Exception {
		Emotions emotions = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity(VisionRequest.assemble(imageUrl), headers);
		ResponseEntity<String> response = restTemplate.exchange(googleKey, HttpMethod.POST, request, String.class);

		if (!Objects.requireNonNull(response.getBody()).equalsIgnoreCase("{}")) {
			JsonNode faceAnnotations = new ObjectMapper().readTree(response.getBody()).get("responses");
			JsonNode joyLikelihood = faceAnnotations.at("/0/faceAnnotations/0/joyLikelihood");
			JsonNode sorrowLikelihood = faceAnnotations.at("/0/faceAnnotations/0/sorrowLikelihood");
			JsonNode angerLikelihood = faceAnnotations.at("/0/faceAnnotations/0/angerLikelihood");
			JsonNode surpriseLikelihood = faceAnnotations.at("/0/faceAnnotations/0/surpriseLikelihood");
			emotions = new Emotions(joyLikelihood.asText(), sorrowLikelihood.asText(), angerLikelihood.asText(),
					surpriseLikelihood.asText());
		}

		return emotions;
	}
}
