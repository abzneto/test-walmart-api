package br.com.walmart.infrastructure.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.walmart.domain.model.map.Map;

public class MapTemplateLoader implements TemplateLoader {
	@Override
	public void load() {
		Fixture.of(Map.class).addTemplate("valid", new Rule() {{
			add("id", sequence(Long.class));
			add("name", random("SP", "RJ", "MG"));
			add("creationDate", instant("now"));
		}});		
	}
}
