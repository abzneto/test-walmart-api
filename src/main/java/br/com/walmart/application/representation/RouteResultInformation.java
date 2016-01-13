package br.com.walmart.application.representation;

import java.text.DecimalFormat;

public class RouteResultInformation {
	private String path;
	private String costFormatted;
	private double cost;

	public RouteResultInformation(String path, double cost) {
		this.path = path;
		this.cost = cost;
		this.costFormatted = new DecimalFormat("#.00").format(cost);
	}

	public String getPath() {
		return path;
	}

	public double getCost() {
		return cost;
	}

	public String getCostFormatted() {
		return costFormatted;
	}	
}
