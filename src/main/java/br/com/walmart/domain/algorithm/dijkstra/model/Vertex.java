package br.com.walmart.domain.algorithm.dijkstra.model;

import java.util.HashMap;
import java.util.Map;

public class Vertex implements Comparable<Vertex> {	   
	private String route = "";

	private String path  = "";

	private double distance = Double.MAX_VALUE; 

	private Vertex previous = null;

	private final Map<Vertex, Double> neighbours = new HashMap<Vertex, Double>();

	public Vertex(String route) {
		this.route = route;
	}

	public int compareTo(Vertex other) {
		return Double.compare(distance, other.distance);
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public Map<Vertex, Double> getNeighbours() {
		return neighbours;
	}
}