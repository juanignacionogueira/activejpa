/**
 * 
 */
package org.activejpa.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolverHolder;

/**
 * @author ganeshs
 *
 */
public class JPA {

	public static final JPA instance = new JPA();

	// @Deprecated
	// private JPAConfig defaultConfig;
	// @Deprecated
	// private Map<String, JPAConfig> configs = new HashMap<String,
	// JPAConfig>();

	private EntityManager manager;

	private String cacheableHint;

	private static final String HIBERNATE_PERSISTENCE = "org.hibernate.ejb.HibernatePersistence";

	private static final String ECLIPSE_PERSISTENCE = "org.eclipse.persistence.jpa.PersistenceProvider";

	private static final String OPENJPA_PERSISTENCE = "org.apache.openjpa.persistence.PersistenceProviderImpl";

	private JPA() {
		List<PersistenceProvider> providers = PersistenceProviderResolverHolder.getPersistenceProviderResolver()
				.getPersistenceProviders();
		if (providers != null) {
			String providerClass = providers.get(0).getClass().getCanonicalName();
			if (providerClass.equals(HIBERNATE_PERSISTENCE)) {
				this.cacheableHint = "org.hibernate.cacheable";
			} else if (providerClass.equals(ECLIPSE_PERSISTENCE)) {
				this.cacheableHint = "eclipselink.query-results-cache";
			} else if (providerClass.equals(OPENJPA_PERSISTENCE)) {
				this.cacheableHint = "openjpa.QueryCache";
			}
		}
	}

	public void addEntityManager(EntityManager manager) {
		this.manager = manager;
	}

	public EntityManager getManager() {
		return this.manager;
	}

	// @Deprecated
	// public void addPersistenceUnit(String persistenceUnitName) {
	// this.addPersistenceUnit(persistenceUnitName, true);
	// }
	//
	// @Deprecated
	// public void addPersistenceUnit(String persistenceUnitName, boolean
	// isDefault) {
	// this.addPersistenceUnit(persistenceUnitName, Collections.<String, String>
	// emptyMap(), isDefault);
	// }
	//
	// @Deprecated
	// public void addPersistenceUnit(String persistenceUnitName, Map<String,
	// String> properties, boolean isDefault) {
	// EntityManagerFactory factory =
	// this.createEntityManagerFactory(persistenceUnitName, properties);
	// this.addPersistenceUnit(persistenceUnitName, factory, isDefault);
	// }
	//
	// @Deprecated
	// public void addPersistenceUnit(String persistenceUnitName,
	// EntityManagerFactory factory) {
	// this.addPersistenceUnit(persistenceUnitName, factory, true);
	// }
	//
	// @Deprecated
	// public void addPersistenceUnit(String persistenceUnitName,
	// EntityManagerFactory factory, boolean isDefault) {
	// JPAConfig config = new JPAConfig(persistenceUnitName, factory);
	// if (isDefault) {
	// this.defaultConfig = config;
	// }
	// this.configs.put(persistenceUnitName, config);
	// }
	//
	// @Deprecated
	// public JPAConfig getConfig(String configName) {
	// return this.configs.get(configName);
	// }
	//
	// /**
	// * @return the defaultConfig
	// */
	// @Deprecated
	// public JPAConfig getDefaultConfig() {
	// return this.defaultConfig;
	// }
	//
	// @Deprecated
	// public void close() {
	// List<JPAConfig> confs = new ArrayList<JPAConfig>();
	// confs.addAll(this.configs.values());
	// for (JPAConfig config : confs) {
	// config.close();
	// this.configs.remove(config.getName());
	// }
	// }
	//
	// @Deprecated
	// protected EntityManagerFactory createEntityManagerFactory(String
	// persistenceUnitName,
	// Map<String, String> properties) {
	// return Persistence.createEntityManagerFactory(persistenceUnitName,
	// properties);
	// }

	/**
	 * @return the cacheableHint
	 */
	public String getCacheableHint() {
		return this.cacheableHint;
	}
}
