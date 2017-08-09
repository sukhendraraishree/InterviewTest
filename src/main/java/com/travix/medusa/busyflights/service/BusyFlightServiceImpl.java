package com.travix.medusa.busyflights.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.http.Client;
import com.travix.medusa.busyflights.http.CommonUtilities;

/**
 * 
 * In this class we are wrapping the response of toughjet and crazyair to
 * busyflightResponse and then sorting them using JAVA 8 Comparator and Using
 * Java 8 foreach
 * 
 */

@Service("busyFlightServiceImpl")
public class BusyFlightServiceImpl implements BusyFlightService {

	@Autowired
	private ResponseServiceImpl responseService;

	public static final Logger logger = LoggerFactory.getLogger(BusyFlightServiceImpl.class);

	public List<BusyFlightsResponse> findAllAvailableFlights(BusyFlightsRequest busyFlightsRequest)
			throws InterruptedException, ExecutionException {

		// ThreadCall
		List<Object> flightResponseList = responseService.mutipleURLParallelCall(busyFlightsRequest);

		List<Object> allFlightsList = new ArrayList<Object>();
		List<BusyFlightsResponse> responseFlightsList = responseService.createBusyFlightsResponse(flightResponseList);

		// Using JAVA 8 features

		responseFlightsList.sort(Comparator.comparing(BusyFlightsResponse::getFare));

		logger.debug("After Sorting");
		responseFlightsList.forEach(list -> logger.debug(list.toString()));

		System.out.println(allFlightsList);

		return responseFlightsList;

	}
}
