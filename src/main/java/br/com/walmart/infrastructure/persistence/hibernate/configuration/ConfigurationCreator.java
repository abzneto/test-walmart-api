package br.com.walmart.infrastructure.persistence.hibernate.configuration;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;

import net.sf.trugger.scan.ClassScan;
import net.sf.trugger.scan.PackageScan;
import net.sf.trugger.scan.ScanLevel;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class ConfigurationCreator implements ComponentFactory<Configuration> {

	private static Logger LOGGER = Logger.getLogger(ConfigurationCreator.class);
	private Configuration cfg;

	public ConfigurationCreator() {
		cfg = new Configuration();
	}

	/**
	 * 
	 * @throws URISyntaxException
	 */
	@PostConstruct
	public void create() throws URISyntaxException {
		configureHibernateEntities();
		configureHerokuAccess();
		cfg.getProperties().setProperty("hibernate.hbm2ddl.auto", "");
	}

	protected URL getHibernateCfgLocation() {
		return getClass().getResource("/hibernate.cfg.xml");
	}

	private void configureHerokuAccess() throws URISyntaxException {		
		cfg.setProperty("hibernate.connection.url",      "jdbc:postgresql://ec2-107-21-223-147.compute-1.amazonaws.com:5432/d3acdqajjjui63?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory" );
		cfg.setProperty("hibernate.connection.username", "dabpfnfboxmtpf");
		cfg.setProperty("hibernate.connection.password", "32MeEe58G9gbjMihrylwjWbZ8P");
	}

	private void configureHibernateEntities() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		PackageScan packageScan = new PackageScan("br.com.walmart", ScanLevel.SUBPACKAGES);
		classes.addAll(ClassScan.findAll().annotatedWith(Entity.class).in(packageScan));

		LOGGER.info("Configuring Hibernate Entities:");
		for(@SuppressWarnings("rawtypes")
		Class cclass : classes) {
			LOGGER.info(cclass.toString());
			cfg.addAnnotatedClass(cclass);
		}
	}

	@Override
	public Configuration getInstance() {
		return cfg;
	}
}
