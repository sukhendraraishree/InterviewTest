package com.travix.medusa.busyflights.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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
 * Service to make thread call and to wrap Object into BusyFlightsResponse 
 *
 */
@Service
public class ResponseServiceImpl implements ResponseService {

	@Autowired
	private CommonUtilities commonUtilities;

	@Value("${toughtjetURL}")
	private String toughtjetURL;

	@Value("${crazyairURL}")
	private String crazyairURL;

	@Value("${firstSupplier}")
	private String firstSupplier;

	@Value("${secondSupplier}")
	private String secondSupplier;

	public List<BusyFlightsResponse> createBusyFlightsResponse(List<Object> allFlightsList) {
		// BusyFlightsResponse busyFlightsResponse =null;
		List<BusyFlightsResponse> responseList = new ArrayList<BusyFlightsResponse>();

		allFlightsList.forEach(Object -> {
			if (Object instanceof CrazyAirResponse) 
			{
				BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((CrazyAirResponse) Object).getAirline());
				busyFlightsResponse.setSupplier(firstSupplier);
				busyFlightsResponse.setFare(((CrazyAirResponse) Object).getPrice());
				busyFlightsResponse.setDepartureAirportCode(((CrazyAirResponse) Object).getDepartureAirportCode());
				busyFlightsResponse.setDestinationAirportCode(((CrazyAirResponse) Object).getDestinationAirportCode());
				busyFlightsResponse.setDepartureDate(((CrazyAirResponse) Object).getDepartureDate());
				busyFlightsResponse.setArrivalDate(((CrazyAirResponse) Object).getArrivalDate());
				responseList.add(busyFlightsResponse);
			} 
			else if (Object instanceof ToughJetResponse) 
			{
				BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((ToughJetResponse) Object).getCarrier());
				busyFlightsResponse.setSupplier(secondSupplier);
				busyFlightsResponse
						.setFare((((ToughJetResponse) Object).getBasePrice() + ((ToughJetResponse) Object).getTax())
								- (((ToughJetResponse) Object).getDiscount() / 100)
										* (((ToughJetResponse) Object).getBasePrice()));
				busyFlightsResponse.setDepartureAirportCode(((ToughJetResponse) Object).getDepartureAirportName());
				busyFlightsResponse.setDestinationAirportCode(((ToughJetResponse) Object).getArrivalAirportName());
				busyFlightsResponse.setDepartureDate(((ToughJetResponse) Object).getOutboundDateTime());
				busyFlightsResponse.setArrivalDate(((ToughJetResponse) Object).getInboundDateTime());

				responseList.add(busyFlightsResponse);
			}

		});
		return responseList;
	}

	@SuppressWarnings("unchecked")
	public List<Object> mutipleURLParallelCall(BusyFlightsRequest busyFlightsRequest)
			throws InterruptedException, ExecutionException {
		List<CrazyAirResponse> threadcrazyAirList = Collections.EMPTY_LIST;
		List<ToughJetResponse> threadtoughJetList = Collections.EMPTY_LIST;
		List<Object> allFlightsList = new ArrayList<Object>();

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

		String hostList1 = crazyairURL ;
        String hostList2 = toughtjetURL ;
		// We can use different approach too but due to time constraint doing it
		// in simple way
		/*for (int i = 0; i < hostList.length; i++) {*/

			
			Client worker1 = new Client(hostList1, busyFlightsRequest);
			Client worker2 = new Client(hostList2, busyFlightsRequest);
			Future<String> result1 = executor.submit(worker1);
			Future<String> result2 = executor.submit(worker2);

			if (result1.get().trim() != null) {
				
					threadcrazyAirList = (List<CrazyAirResponse>) commonUtilities.objectCrazyAir(result1.get());
				} 
			if (result2.get().trim() != null){
					threadtoughJetList = (List<ToughJetResponse>) commonUtilities.objectToughJet(result2.get());
				}
		

		
		if (threadcrazyAirList != null && !threadcrazyAirList.isEmpty())
			allFlightsList.addAll(threadcrazyAirList);
		if (threadtoughJetList != null && !threadtoughJetList.isEmpty())
			allFlightsList.addAll(threadtoughJetList);

		return allFlightsList;
	}
}
