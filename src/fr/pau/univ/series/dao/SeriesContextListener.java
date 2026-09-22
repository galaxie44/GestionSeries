package fr.pau.univ.series.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Initialise l'EntityManagerFactory au démarrage.
 * Les EntityManager sont créés par requête (voir {@link EntityManagerFilter}).
 */
@WebListener
public class SeriesContextListener implements ServletContextListener {

	private static EntityManagerFactory emf;
	private static final ThreadLocal<EntityManager> CURRENT_EM = new ThreadLocal<>();

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		try {
			emf = Persistence.createEntityManagerFactory("GestionSeries");
			System.out.println("[GestionSeries] EntityManagerFactory JPA initialisé.");
			final EntityManager bootstrapEm = emf.createEntityManager();
			try {
				bind(bootstrapEm);
				fr.pau.univ.series.dao.impl.bdd.DataSeeder.seedIfEmpty();
			} finally {
				clear();
				if (bootstrapEm.isOpen()) {
					bootstrapEm.close();
				}
			}
		} catch (final Exception e) {
			System.err.println("[GestionSeries] Impossible d'initialiser JPA : " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		clear();
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
		System.out.println("[GestionSeries] EntityManagerFactory JPA fermé.");
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	@Deprecated
	public static EntityManager getEntityManager() {
		return getCurrentEntityManager();
	}

	public static EntityManager getCurrentEntityManager() {
		return CURRENT_EM.get();
	}

	public static void bind(final EntityManager em) {
		CURRENT_EM.set(em);
	}

	public static void clear() {
		CURRENT_EM.remove();
	}
}
