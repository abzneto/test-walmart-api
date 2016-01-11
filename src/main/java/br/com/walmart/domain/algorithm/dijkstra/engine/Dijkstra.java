package br.com.walmart.domain.algorithm.dijkstra.engine;

import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import br.com.walmart.domain.algorithm.dijkstra.model.Vertex;

public class Dijkstra {

	private boolean success = true;

	public boolean sucess() {
		return this.success;
	}

	public void process(Map<String, Vertex> graph, String startName) {
		if (!graph.containsKey(startName)) {
			success = false;
			return;
		}

		final Vertex origin = graph.get(startName);

		NavigableSet<Vertex> vertexSET = new TreeSet<Vertex>();

		for ( final Vertex vertex : graph.values() ) {
			vertex.setPrevious(vertex == origin ? origin : null);
			vertex.setDistance( vertex == origin ? 0d : Double.MAX_VALUE);

			vertexSET.add(vertex);
		}

		this.dijkstra( vertexSET );
	}

	private void dijkstra(final NavigableSet<Vertex> vertexSET) {      
		Vertex vertexOrigin = null;
		Vertex vertexDestiny = null;

		boolean first = true;

		while (!vertexSET.isEmpty()) {	 
			vertexOrigin = vertexSET.pollFirst();

			if (vertexOrigin.getDistance() == Integer.MAX_VALUE) {
				break;
			}

			if (first) {
				vertexOrigin.setPath( vertexOrigin.getPath() + vertexOrigin.getRoute() );
			}

			first = false;

			for (Map.Entry<Vertex, Double> vertexEntry : vertexOrigin.getNeighbours().entrySet()) {
				vertexDestiny = vertexEntry.getKey(); 

				double alternateDist = vertexOrigin.getDistance() + vertexEntry.getValue();

				String path = vertexOrigin.getPath() + " -> " + vertexEntry.getKey().getRoute();

				if ( alternateDist < vertexDestiny.getDistance() ) { 
					vertexSET.remove(vertexDestiny);
					vertexDestiny.setDistance( alternateDist );      
					vertexDestiny.setPath(path);
					vertexDestiny.setPrevious(vertexOrigin);
					vertexSET.add(vertexDestiny);
				} 
			}
		}
	}
}

