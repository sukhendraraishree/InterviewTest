package com.travix.medusa.busyflights.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Properties class to inject External Data of Tough jet and Crazy Air from
 * global.properties file for extensibility and customization
 */
@Component
@PropertySource("classpath:global.properties")
@ConfigurationProperties
public class GlobalProp {

	private String toughtjetURL;
	private String crazyairURL;
	private String firstSupplier;
	private String secondSupplier;

	public String getToughtjetURL() {
		return toughtjetURL;
	}

	public void setToughtjetURL(String toughtjetURL) {
		this.toughtjetURL = toughtjetURL;
	}

	public String getCrazyairURL() {
		return crazyairURL;
	}

	public void setCrazyairURL(String crazyairURL) {
		this.crazyairURL = crazyairURL;
	}

	public String getFirstSupplier() {
		return firstSupplier;
	}

	public void setFirstSupplier(String firstSupplier) {
		this.firstSupplier = firstSupplier;
	}

	public String getSecondSupplier() {
		return secondSupplier;
	}

	public void setSecondSupplier(String secondSupplier) {
		this.secondSupplier = secondSupplier;
	}

}
