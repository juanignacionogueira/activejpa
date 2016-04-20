/**
 * 
 */
package org.activejpa.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * @author ganeshs
 *
 */
@Deprecated
public class JPAConfig {

	private final String name;

	private final EntityManagerFactory entityManagerFactory;

	private ThreadLocal<JPAContext> currentContext = new ThreadLocal<JPAContext>();

	public JPAConfig(String name, EntityManagerFactory factory) {
		this.name = name;
		this.entityManagerFactory = factory;
	}

	public void close() {
		try {
			this.entityManagerFactory.close();
		} catch (Exception e) {
			// Suppress exception and log
		}
	}

	public JPAContext getContext() {
		return this.getContext(false);
	}

	public JPAContext getContext(boolean readOnly) {
		JPAContext context = this.currentContext.get();
		if (context == null) {
			context = new JPAContext(this, readOnly);
			this.currentContext.set(context);
		}
		return context;
	}

	protected void clearContext() {
		this.currentContext.remove();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the entityManagerFactory
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManagerFactory;
	}
}
