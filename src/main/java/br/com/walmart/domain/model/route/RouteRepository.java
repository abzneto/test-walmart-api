package br.com.walmart.domain.model.route;

import java.util.List;

import br.com.walmart.domain.model.map.Map;

public interface RouteRepository {
	List<Route> loadByMap(Map map);

	void add(Route route);

	Route loadById(Long id);

	void update(Route route);
	
	void remove(Route route);
}

