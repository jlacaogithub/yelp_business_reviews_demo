package com.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.model.Business;
import com.demo.service.BusinessService;
import com.demo.util.BusinessHelper;

@Component
public class BusinessServiceImpl implements BusinessService{

private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
	
	@Autowired
	BusinessHelper businessHelper;
	
	@Override
	public List<Business> getBusinesses(String term, String location) {
		String method = "getBusinesses("+term+","+location+")";
		logger.info("in : " + method);	
		List<Business> businesses = null;
		try {
			//get the business information
			businesses = businessHelper.getBusinesses(term, location);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(
					method+":::Exception caught while calling business helper, reason ::: ",
					e);
		}
		
		logger.info("out : " + method);
		
		return businesses;
	}
	
}
