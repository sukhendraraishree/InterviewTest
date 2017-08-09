package com.travix.medusa.busyflights.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;

/*
 * Web Service Client to invoke Crazy and Tough jet airline URL
 */

public class Client implements Callable<String> {

	public static final Logger logger = LoggerFactory.getLogger(Client.class);

	private String serviceLocation;
	private String httpURL;
	private final String url;
	private BusyFlightsRequest request;

	public Client(String url, BusyFlightsRequest busyFlightsRequest) {
		this.url = url;
		this.request = busyFlightsRequest;
	}

	@Override
	public String call() {
		serviceLocation = request.getOrigin() + "/" + request.getDestination() + "/" + request.getDepartureDate() + "/"
				+ request.getReturnDate() + "/" + request.getNumberOfPassengers();

		httpURL = url + serviceLocation;
		StringBuffer responseList = new StringBuffer();

		try {
			URL obj = new URL(httpURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			logger.debug("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) // success
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					responseList.append(inputLine);
				}
				in.close();
				logger.debug("Response from  WS" + "\n " + responseList);
				con.disconnect();
			}
		} catch (Exception e) {
			logger.error("Connection Error for " + url + e);

		}
		return responseList.toString();
	}
}