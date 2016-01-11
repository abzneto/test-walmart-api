package br.com.walmart.infrastructure.persistence.hibernate;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.walmart.domain.model.map.Map;
import br.com.walmart.domain.model.map.MapRepository;

@Component
@RequestScoped
public class MapDao implements MapRepository {

	private final Session session;

	public MapDao(Session session) {
		this.session = session;
	}

	@Override
	public void add(Map map) {
		session.save(map);
	}

	@Override
	public Map loadById(Long id) {
		return (Map) session.get(Map.class, id);
	}

	@Override
	public void remove(Map map) {
		session.delete(map);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> load() {
		return session.createCriteria(Map.class).list();
	}

	@Override
	public void update(Map map) {
		session.update(map);		
	}
}
