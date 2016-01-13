package br.com.walmart.infrastructure.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.walmart.domain.model.map.Map;
import br.com.walmart.domain.model.route.Route;

public class RouteTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Route.class)
			.addTemplate("route1", new Rule() {{
				add("id", sequence(Long.class));
				add("origin", "A");
				add("destination", "B");
				add("distance", 10d);			
				add("map", one(Map.class, "valid"));
				add("creationDate", instant("now"));
			}})
			.addTemplate("route2", new Rule() {{
				add("id", sequence(Long.class));
				add("origin", "B");
				add("destination", "D");
				add("distance", 15d);			
				add("map", one(Map.class, "valid"));
				add("creationDate", instant("now"));
			}})
			.addTemplate("route3", new Rule() {{
				add("id", sequence(Long.class));
				add("origin", "A");
				add("destination", "C");
				add("distance", 20d);			
				add("map", one(Map.class, "valid"));
				add("creationDate", instant("now"));
			}})
			.addTemplate("route4", new Rule() {{
				add("id", sequence(Long.class));
				add("origin", "C");
				add("destination", "D");
				add("distance", 30d);			
				add("map", one(Map.class, "valid"));
				add("creationDate", instant("now"));
			}})
			.addTemplate("route5", new Rule() {{
				add("id", sequence(Long.class));
				add("origin", "B");
				add("destination", "E");
				add("distance", 50d);			
				add("map", one(Map.class, "valid"));
				add("creationDate", instant("now"));
			}})
			.addTemplate("route6", new Rule() {{
				add("id", sequence(Long.class));
				add("origin", "D");
				add("destination", "E");
				add("distance", 30d);			
				add("map", one(Map.class, "valid"));
				add("creationDate", instant("now"));
			}});		
	}
}
