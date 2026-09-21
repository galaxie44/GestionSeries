package fr.pau.univ.series.dao.impl.bdd;

import fr.pau.univ.series.dao.SeriesContextListener;
import fr.pau.univ.series.exception.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Singleton d'accès à l'EntityManager JPA (PDF p.41–42, 60).
 * La DaoFactory ne touche pas directement à la BDD : elle délègue aux DAO,
 * qui utilisent ce helper.
 */
public class DaoBddHelper {

	private static DaoBddHelper instance;
	private final EntityManager em;

	private DaoBddHelper() throws DaoException {
		try {
			this.em = SeriesContextListener.getEntityManager();
			if (this.em != null) {
				System.out.println("EntityManager créé.");
			} else {
				System.out.println(
						"[DaoBddHelper] EntityManager indisponible pour l'instant (JPA/MySQL). Mode mémoire actif.");
			}
		} catch (final Exception e) {
			throw new DaoException("Impossible de créer l'EntityManager.", e);
		}
	}

	/**
	 * @return l'unique instance de {@link DaoBddHelper}
	 * @throws DaoException si l'initialisation échoue
	 */
	public static synchronized DaoBddHelper getInstance() throws DaoException {
		if (instance == null) {
			instance = new DaoBddHelper();
		}
		return instance;
	}

	/**
	 * @return l'EntityManager JPA
	 * @throws DaoException si JPA n'est pas initialisé
	 */
	public EntityManager getEm() throws DaoException {
		if (this.em == null) {
			throw new DaoException(
					"EntityManager null : vérifier MySQL (WAMP), persistence.xml et les dépendances Maven.");
		}
		return this.em;
	}

	/** @return {@code true} si JPA est opérationnel */
	public boolean isJpaReady() {
		return this.em != null;
	}

	public void beginTransaction() throws DaoException {
		final EntityTransaction trans = getEm().getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
	}

	public void commitTransaction() throws DaoException {
		final EntityTransaction trans = getEm().getTransaction();
		if (trans.isActive()) {
			trans.commit();
		}
	}

	public void rollbackTransaction() {
		try {
			if (this.em == null) {
				return;
			}
			final EntityTransaction trans = this.em.getTransaction();
			if (trans.isActive()) {
				trans.rollback();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
