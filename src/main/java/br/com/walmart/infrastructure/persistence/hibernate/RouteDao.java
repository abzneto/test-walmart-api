package br.com.walmart.infrastructure.persistence.hibernate;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.walmart.domain.model.map.Map;
import br.com.walmart.domain.model.route.Route;
import br.com.walmart.domain.model.route.RouteRepository;

@Component
@RequestScoped
public class RouteDao implements RouteRepository {

	private final Session session;

	public RouteDao(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Route> loadByMap(Map map) {
		return session.createCriteria(Route.class)
				.add(eq("map", map))
				.list();
	}

	@Override
	public void add(Route route) {
		session.save(route);		
	}

	@Override
	public Route loadById(Long id) {
		return (Route) session.get(Route.class, id);
	}

	@Override
	public void remove(Route route) {
		session.delete(route);		
	}

	@Override
	public void update(Route route) {
		session.update(route);	
	}
}
