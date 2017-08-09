package com.travix.medusa.busyflights.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

public interface ResponseService {
	
	List<Object> mutipleURLParallelCall(BusyFlightsRequest busyFlightsRequest) throws InterruptedException, ExecutionException ;
	
	List<BusyFlightsResponse> createBusyFlightsResponse(List<Object> allFlightsList);

}
