package com.demo.service;

import com.demo.model.Business;

public interface CustomerReviewService {

	/**
	 * Method that will retrieve all reviews of the business
	 * 
	 * @param businessId {@link String} business id
	 * @return {@link Business}
	 */
	Business getBusinessReviews(String businessId);
}
