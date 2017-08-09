package com.travix.medusa.busyflights.http;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

/*
 * common functionality to get List from Response 
 * as well as for any future amendments
 * */
@Component("commonUtilities")
public class CommonUtilities {

	public CommonUtilities() {
	}

	public List<CrazyAirResponse> objectCrazyAir(String responseCrazy) {
		List<CrazyAirResponse> list = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(responseCrazy, new TypeReference<List<CrazyAirResponse>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<ToughJetResponse> objectToughJet(String responseToughJet) {
		List<ToughJetResponse> list = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(responseToughJet, new TypeReference<List<ToughJetResponse>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
