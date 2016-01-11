package br.com.walmart.application.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.walmart.application.representation.RouteInformation;
import br.com.walmart.domain.model.map.Map;
import br.com.walmart.domain.model.map.MapRepository;
import br.com.walmart.domain.model.route.Route;
import br.com.walmart.domain.model.route.RouteRepository;

@Resource
public class RouteController {

	private final Result result;
	private final RouteRepository routeRepository;
	private final MapRepository mapRepository;

	public RouteController(Result result, RouteRepository routeRepository, MapRepository mapRepository){
		this.result = result;
		this.routeRepository = routeRepository;
		this.mapRepository = mapRepository;
	}

	@Get("/route/{mapId}")
	public void loadByMap(Long mapId) {
		Map map = mapRepository.loadById(mapId);

		if (map == null) {
			result.notFound();
			return;
		}

		result.use(json()).from(routeRepository.loadByMap(map), "routes").recursive().serialize();
	}

	@Post("/route")
	@Consumes("application/json")
	public void add(List<RouteInformation> routes, Long mapId) {	
		Map map = mapRepository.loadById(mapId);
		
		if (map == null) {
			result.notFound();
			return;
		}

//		for ( RouteInformation routeInfo : routes ) {
//			Route route = routeRepository.loadById(routeInfo.getId());
//			
//			boolean isNEW = false;
//			
//			if (route == null) {
//				route = new Route();
//				
//				isNEW = true;
//			}
//			
//			route.setMap(map);
//			route.setDestination(routeInfo.getDestination());
//			route.setDistance(routeInfo.getDistance());
//			route.setOrigin(routeInfo.getOrigin());
//			
//			if (isNEW) {
//				routeRepository.add(route);
//			} else {
//				routeRepository.update(route);
//			}
//		}		

		result.use(json()).from(true, "success").recursive().serialize();
	}

	@Delete("/route/{routeId}")
	public void remove(Long routeId) {
		Route route = routeRepository.loadById(routeId);

		if (route == null) {
			result.notFound();
			return;
		}

		routeRepository.remove(route);		
		result.use(json()).from(true, "success").recursive().serialize();
	}
}
