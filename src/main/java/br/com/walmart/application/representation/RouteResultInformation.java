package br.com.walmart.application.representation;

public class RouteResultInformation {
	private String path;
	private String cost;

	public RouteResultInformation(String path, String cost) {
		this.path = path;
		this.cost = cost;
	}

	public String getPath() {
		return path;
	}

	public String getCost() {
		return cost;
	}
}
