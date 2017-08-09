package com.travix.medusa.busyflights.validation;

public interface SearchValidator {
	
	boolean validateRequest(String origin, String destination, String departureDate, String returnDate,
			int numberOfPassengers);

}
