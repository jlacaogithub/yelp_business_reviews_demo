package com.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.model.Business;
import com.demo.service.CustomerReviewService;
import com.demo.util.BusinessHelper;

@Component
public class CustomerReviewServiceImpl implements CustomerReviewService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerReviewServiceImpl.class);
	
	@Autowired
	BusinessHelper businessHelper;
	
	@Override
	public Business getBusinessReviews(String businessId) {
		String method = "getBusinessReviews()";
		logger.info("in : " + method);	
		Business business = null;
		try {
			//get the business information
			business = businessHelper.getBusinessInformation(businessId);
			
			if(business!=null){
				//get the business reviews and populate
				business.setReviews(businessHelper.getBusinessReviews(businessId));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(
					method+":::Exception caught while calling business helper, reason ::: ",
					e);
		}
		
		logger.info("out : " + method);
		
		return business;
	}

}
