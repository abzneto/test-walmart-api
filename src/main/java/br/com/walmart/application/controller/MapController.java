package br.com.walmart.application.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.walmart.application.representation.RouteResultInformation;
import br.com.walmart.domain.model.map.Map;
import br.com.walmart.domain.model.map.MapRepository;
import br.com.walmart.domain.model.route.Route;
import br.com.walmart.domain.model.route.RouteRepository;
import br.com.walmart.domain.services.route.RouteCalculator;

@Resource
public class MapController {
	private final Result result;
	private final MapRepository mapRepository;
	private final RouteCalculator routeCalculator;
	private final RouteRepository routeRepository;

	public MapController(Result result, MapRepository mapRepository, RouteRepository routeRepository, RouteCalculator routeCalculator){
		this.result = result;
		this.mapRepository = mapRepository;
		this.routeCalculator = routeCalculator;
		this.routeRepository = routeRepository;
	}

	@Get("/map")
	public void load() {
		result.use(json()).from(mapRepository.load(), "maps").recursive().serialize();
	}

	@Post("/map")
	public void add(Long mapId, String mapName) {	
		Map map = null;
		
		if (mapId != null) { 
			map = mapRepository.loadById(mapId);
		}

		if (map == null) {
			map = new Map();
			
			map.setName(mapName);
			
			mapRepository.add(map);
		} else {
			map.setName(mapName);
			
			mapRepository.update(map);
		}

		result.use(json()).from(map.getId(), "mapId").recursive().serialize();
	}

	@Delete("/map/{mapId}")
	public void remove(Long mapId) {
		Map map = mapRepository.loadById(mapId);

		if (map == null) {
			result.notFound();
			return;
		}
		
		List<Route> routes = routeRepository.loadByMap(map);
		
		if (routes != null && !routes.isEmpty()) {
			for (Route route : routes) {
				routeRepository.remove(route);
			}
		}

		mapRepository.remove(map);		
		result.use(json()).from(true, "success").recursive().serialize();
	}

	@Get("/map/{mapId}/calculate")
	public void calculate(Long mapId, String origin, String destination, double autonomy, double valueLiter) {
		Map map = mapRepository.loadById(mapId);

		if (map == null) {
			result.notFound();
			return;
		}

		RouteResultInformation info = routeCalculator.calculate(routeRepository.loadByMap(map), origin, destination, autonomy, valueLiter);
		
		result.use(json()).from(info, "results").recursive().serialize();
	}
}
