package com.travix.medusa.busyflights.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

/**
 * Service class to get AllAvailableFlights
 * 
 */
public interface BusyFlightService {
	
	List<BusyFlightsResponse> findAllAvailableFlights(BusyFlightsRequest busyFlightsRequest) throws InterruptedException, ExecutionException;
	
	
}
