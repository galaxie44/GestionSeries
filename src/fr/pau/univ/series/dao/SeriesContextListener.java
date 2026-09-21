package fr.pau.univ.series.dao;

import fr.pau.univ.series.dao.impl.bdd.DataSeeder;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Initialise l'EntityManager JPA au démarrage de Tomcat (PDF p.39–40).
 */
@WebListener
public class SeriesContextListener implements ServletContextListener {

	private static EntityManager em;
	private static EntityManagerFactory emf;

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		try {
			emf = Persistence.createEntityManagerFactory("GestionSeries");
			em = emf.createEntityManager();
			System.out.println("[GestionSeries] EntityManager JPA initialisé.");
			// Force l'init du helper puis seed si besoin
			DaoBddHelper.getInstance();
			DataSeeder.seedIfEmpty();
		} catch (final Exception e) {
			System.err.println("[GestionSeries] Impossible d'initialiser JPA : " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		if (em != null && em.isOpen()) {
			em.close();
		}
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
		System.out.println("[GestionSeries] EntityManager JPA fermé.");
	}

	public static EntityManager getEntityManager() {
		return em;
	}
}
