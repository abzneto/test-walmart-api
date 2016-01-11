package br.com.walmart.infrastructure.persistence.hibernate.configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.UnknownServiceException;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class SessionFactoryCreator implements ComponentFactory<SessionFactory> {

    private final Configuration cfg;
    private SessionFactory sessionFactory;

    public SessionFactoryCreator(Configuration cfg) {
        this.cfg = cfg;
    }

    @PostConstruct
    public void create() {
    	StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
        sb.applySettings(cfg.getProperties());
        StandardServiceRegistry standardServiceRegistry = sb.build();                   
        sessionFactory = cfg.buildSessionFactory(standardServiceRegistry);
    }

    @PreDestroy
    public void destroy() {
        if (!sessionFactory.isClosed()) {
        	try {
        		sessionFactory.close();
        	} catch(UnknownServiceException e) {
        	}
        }
    }

    @Override
	public SessionFactory getInstance() {
        return sessionFactory;
    }
}
