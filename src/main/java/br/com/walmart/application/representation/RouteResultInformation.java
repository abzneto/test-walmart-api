package br.com.walmart.application.representation;

public class RouteResultInformation {
	private String path;
	private double cost;

	public RouteResultInformation(String path, double cost) {
		this.path = path;
		this.cost = cost;
	}

	public String getPath() {
		return path;
	}

	public double getCost() {
		return cost;
	}
}
