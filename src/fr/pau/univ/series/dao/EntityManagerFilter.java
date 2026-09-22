package fr.pau.univ.series.dao;

import java.io.IOException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

/**
 * Ouvre un EntityManager par requête HTTP et le ferme ensuite (thread-safe).
 */
@WebFilter("/*")
public class EntityManagerFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final EntityManagerFactory emf = SeriesContextListener.getEntityManagerFactory();
		if (emf == null) {
			chain.doFilter(request, response);
			return;
		}

		final EntityManager em = emf.createEntityManager();
		SeriesContextListener.bind(em);
		try {
			chain.doFilter(request, response);
		} finally {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (final Exception ignored) {
				// ignore
			}
			if (em.isOpen()) {
				em.close();
			}
			SeriesContextListener.clear();
		}
	}
}
