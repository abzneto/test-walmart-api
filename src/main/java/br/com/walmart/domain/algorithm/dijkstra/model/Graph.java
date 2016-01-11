package br.com.walmart.domain.algorithm.dijkstra.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private final Map<String, Vertex> graph; 
	private double distance;
	private String path;
	
	public Graph(List<Edge> edges) {
		graph = new HashMap<String, Vertex>(edges.size());

		for (Edge edge : edges) {
			if (!graph.containsKey(edge.origin)) {
				graph.put(edge.origin, new Vertex(edge.origin));
			}

			if (!graph.containsKey(edge.destination)) {
				graph.put(edge.destination, new Vertex(edge.destination));
			}
		}

		for (Edge edge : edges) {
			graph.get(edge.origin).getNeighbours().put(graph.get(edge.destination), edge.distance);
		}
	}

	public void build(String endName) {
		if (!graph.containsKey(endName)) {
			return;
		}

		Vertex result = graph.get(endName);

		this.path = result.getPath();
		this.distance = result.getDistance();
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Vertex> getGraph() {
		return graph;
	}	
}