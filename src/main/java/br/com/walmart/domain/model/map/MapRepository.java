package br.com.walmart.domain.model.map;

import java.util.List;

public interface MapRepository {
	List<Map> load();

	void add(Map map);

	Map loadById(Long id);

	void update(Map map);
	
	void remove(Map map);
}
