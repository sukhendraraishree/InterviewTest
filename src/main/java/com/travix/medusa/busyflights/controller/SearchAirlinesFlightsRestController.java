package com.travix.medusa.busyflights.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.error.CustomErrorType;
import com.travix.medusa.busyflights.service.BusyFlightServiceImpl;
import com.travix.medusa.busyflights.validation.SearchValidatorImpl;

/**
 * Controller for this project gives JSON response having some validations
 */
@RestController
@RequestMapping("/busyflightApi")
public class SearchAirlinesFlightsRestController {

	public static final Logger logger = LoggerFactory.getLogger(SearchAirlinesFlightsRestController.class);

	@Autowired
	private BusyFlightServiceImpl busyFlightServiceImpl;

	@Autowired
	private BusyFlightsRequest busyFlightsrequest;

	@Autowired
	private SearchValidatorImpl searchValidator;

	private boolean validate;

	/*
	 * This method is the main method called browser
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/search/{origin}/{destination}/{departureDate}/{returnDate}/{numberOfPassengers}", method = RequestMethod.GET)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> getFlights(@PathVariable("origin") String origin,
			@PathVariable("destination") String destination, @PathVariable("departureDate") String departureDate,
			@PathVariable("returnDate") String returnDate, @PathVariable("numberOfPassengers") int numberOfPassengers)
			throws Exception {
		logger.info(
				"Fetching available Airline Flights  with origin {} , destination {} , departureDate {}, returnDate {}, numberOfPassengers {} ::",
				origin, destination, departureDate, returnDate, numberOfPassengers);

		List<BusyFlightsResponse> flightsSearchData = Collections.EMPTY_LIST;

		busyFlightsrequest.setOrigin(origin);
		busyFlightsrequest.setDestination(destination);
		busyFlightsrequest.setDepartureDate(departureDate);
		busyFlightsrequest.setReturnDate(returnDate);
		busyFlightsrequest.setNumberOfPassengers(numberOfPassengers);

		// Validation Service Call
		validate = searchValidator.validateRequest(origin, destination, departureDate, returnDate, numberOfPassengers);

		if (validate) {

			// BusyFlight Response Service Call
			flightsSearchData = busyFlightServiceImpl.findAllAvailableFlights(busyFlightsrequest);

			if (flightsSearchData == null || flightsSearchData.isEmpty()) {
				logger.error(
						"Flight search with origin {} , destination {} , departureDate {}, returnDate {}, numberOfPassengers {}  not found. ::",
						origin, destination, departureDate, returnDate, numberOfPassengers);

				return new ResponseEntity(
						new CustomErrorType("Flight search with origin " + origin + " destination " + destination
								+ " departureDate " + departureDate + " returnDate " + returnDate
								+ " numberOfPassengers " + numberOfPassengers + " not found"),
						HttpStatus.NOT_FOUND);
			}
		}

		else {
			logger.error(
					"Flight search with origin {} , destination {} , departureDate {}, returnDate {}, numberOfPassengers {}  not found. ::",
					origin, destination, departureDate, returnDate, numberOfPassengers);

			return new ResponseEntity(
					new CustomErrorType("Flight search with origin " + origin + " destination " + destination
							+ " departureDate " + departureDate + " returnDate " + returnDate
							+ " passengers limit should be less than 4" + " \n " + " Request Should be like \n"
							+ "http://localhost:8888/busyflightApi/search/{origin}/{destination}/{departureDate}/{returnDate}/{numberOfPassengers}"),
					HttpStatus.EXPECTATION_FAILED);

		}

		return new ResponseEntity<List<BusyFlightsResponse>>(flightsSearchData, HttpStatus.OK);
	}

}
