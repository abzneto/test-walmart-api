package br.com.walmart.domain.algorithm.dijkstra.model;

public class Edge {   
	public final String origin, destination;
	public final double distance;

	public Edge(String origin, String destination, double distance) {
		this.origin = origin;
		this.destination = destination;
		this.distance = distance;
	}   
}
