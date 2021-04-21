package com.demo.service;

import java.util.List;

import com.demo.model.Business;

public interface BusinessService {

	/**
	 * This method search for businesses based on term and location
	 * @param term {@link String}
	 * @param location {@link String}
	 * @return {@link List}<{@link Business}
	 */
	List<Business> getBusinesses(String term, String location);
}
