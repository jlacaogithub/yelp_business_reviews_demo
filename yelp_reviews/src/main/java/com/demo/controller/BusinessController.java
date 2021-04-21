package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Business;
import com.demo.model.ErrorResponse;
import com.demo.service.BusinessService;
import com.demo.service.CustomerReviewService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

/**
 * Handles request and response related to business
 * 
 * @author Joseph
 *
 */
@RestController
@RequestMapping(value = "/business")
public class BusinessController {
	private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

	@Autowired
	CustomerReviewService customerReviewService;
	
	@Autowired
	BusinessService businessService;

	@ApiOperation(value = "This API retrieves the business information.", notes = "Accepts business id and returns it's information such as name,url,phone and etc.", response = Business.class, responseContainer = "Business", consumes = "application/json")
	@GetMapping(value = "/reviews/{id}")
	public Object getBusinessReviews(@PathVariable("id") String businessId) {
		String response = "";
		String method = "getBusinessReviews()";
		logger.debug("in : " + method + " : for the id : " + businessId);

		try {
			Business business = customerReviewService.getBusinessReviews(businessId);
				if(business!=null) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.setSerializationInclusion(Include.NON_NULL);
					mapper.setSerializationInclusion(Include.NON_EMPTY);

					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(business);
				}
			response = generateErrorResponse("ValidationError", "Business does not exists");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(method + ":::Unable to retrieve business reviews, reason ::: ", e);
			response = generateErrorResponse("Error", "An error occur while retrieving business reviews");
		}
		logger.debug("out : " + method);
		return response;
	}

	@ApiOperation(value = "This API retrieves businesses information.", notes = "Accepts term,location and returns businesses information such as name,url,phone and etc.", response = Business.class, responseContainer = "List", consumes = "application/json")
	@PostMapping(value = "/search")
	public Object getBusinesses(@RequestBody Map<String, String> requestData) {
		String response = "";
		String method = "getBusinesses()";
		String term = "";
		String location = "";
		if(requestData!=null) {
			term = requestData.get("term");
			location = requestData.get("location");
		}
		logger.debug("in : " + method + " : for the term/location : " + term + "/" + location);

		try {
				if(isFieldEmpty(term) || isFieldEmpty(location) ) {
					response = generateErrorResponse("ValidationError", "Term or Location is empty");
				}else {
					List<Business> business = businessService.getBusinesses(term, location);
					ObjectMapper mapper = new ObjectMapper();
					mapper.setSerializationInclusion(Include.NON_NULL);
					mapper.setSerializationInclusion(Include.NON_EMPTY);

					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(business);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(method + ":::Unable to retrieve business reviews, reason ::: ", e);
			response = generateErrorResponse("Error", "An error occur while retrieving businesses");
		}
		logger.debug("out : " + method);
		return response;
	}
	
	
	private boolean isFieldEmpty(String field) {
		boolean res = true;
		if(field!=null && field.length()>0) {
			res=false;
		}
		return res;
	}
	
	private String generateErrorResponse(String type, String message) {
		String response = "";
		
		ErrorResponse error = new ErrorResponse();
		error.setResponseType(type);
		error.setMessage(message);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);

		try {
			response =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
