package fr.pau.univ.series.dao;

import fr.pau.univ.series.exception.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Initialise l'EntityManager JPA au démarrage de l'application Tomcat
 * et le libère à l'arrêt (PDF p.39–40).
 */
@WebListener
public class SeriesContextListener implements ServletContextListener {

	private static EntityManager em;
	private static EntityManagerFactory emf;

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		try {
			// "GestionSeries" = nom de la persistence-unit dans persistence.xml
			emf = Persistence.createEntityManagerFactory("GestionSeries");
			em = emf.createEntityManager();
			System.out.println("[GestionSeries] EntityManager JPA initialisé.");
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

	/**
	 * @return l'EntityManager unique de l'application, ou {@code null} si l'init a échoué
	 */
	public static EntityManager getEntityManager() {
		return em;
	}
}
