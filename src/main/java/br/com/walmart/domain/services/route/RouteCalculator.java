package br.com.walmart.domain.services.route;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.walmart.application.representation.RouteResultInformation;
import br.com.walmart.domain.algorithm.dijkstra.engine.Dijkstra;
import br.com.walmart.domain.algorithm.dijkstra.model.Edge;
import br.com.walmart.domain.algorithm.dijkstra.model.Graph;
import br.com.walmart.domain.model.route.Route;

@Component
@RequestScoped
public class RouteCalculator {

	public RouteResultInformation calculate(List<Route> routes, String origin, String destination, double autonomy, double valueLiter) {		
		List<Edge> edges = new ArrayList<Edge>();

		for (Route route : routes) {
			edges.add( new Edge( route.getOrigin(), route.getDestination(), route.getDistance() ) );
		}

		Graph graph = new Graph(edges);

		Dijkstra dijkstra = new Dijkstra();

		dijkstra.process(graph.getGraph(),origin);

		if (dijkstra.sucess()) {
			graph.build(destination);

			return new RouteResultInformation( graph.getPath(), costCalculatorFormatted(graph.getDistance(), autonomy, valueLiter) );
		}      

		return null;
	}

	private String costCalculatorFormatted(double distance, double autonomy, double valueLiter) {
		double cost = ( distance / autonomy ) * valueLiter;
		
		return new DecimalFormat("#.00").format(cost);
	}
}
