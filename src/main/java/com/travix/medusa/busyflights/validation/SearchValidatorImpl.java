package com.travix.medusa.busyflights.validation;

import org.springframework.stereotype.Service;

/**
 * Validation Service checking URL values, etc 
 *
 */
@Service
public class SearchValidatorImpl implements SearchValidator {
	
	boolean validate;
	
	public boolean validateRequest(String origin, String destination, String departureDate, String returnDate,
			int numberOfPassengers) {
		if (numberOfPassengers > 4 || numberOfPassengers < 1 || origin.trim() == null || destination.trim() == null
				|| departureDate.trim() == null || returnDate.trim() == null) 
		{
			return validate;
		} 
		else 
		{
			validate = true;
			return validate;
		}

	}

}
