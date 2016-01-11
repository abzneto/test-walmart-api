package br.com.walmart.application.transactionManagement;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class HibernateTransactionInterceptor implements Interceptor {

    private final Session session;
    private final Validator validator;

    public HibernateTransactionInterceptor(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
    }

    @Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object instance) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            stack.next(method, instance);
            if (!validator.hasErrors() && transaction.isActive()) {
                transaction.commit();
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
	public boolean accepts(ResourceMethod method) {
        return true;
    }
}
