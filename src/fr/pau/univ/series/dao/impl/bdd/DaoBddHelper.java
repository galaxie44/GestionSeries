package fr.pau.univ.series.dao.impl.bdd;

import fr.pau.univ.series.dao.SeriesContextListener;
import fr.pau.univ.series.exception.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

/**
 * Singleton d'accès à l'EntityManager JPA (PDF p.41–42, 60, 68).
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
				throw new DaoException(
						"EntityManager null : vérifier MySQL (WAMP), persistence.xml et les jars WEB-INF/lib.");
			}
		} catch (final DaoException e) {
			throw e;
		} catch (final Exception e) {
			throw new DaoException("Impossible de créer l'EntityManager.", e);
		}
	}

	public static synchronized DaoBddHelper getInstance() throws DaoException {
		if (instance == null) {
			instance = new DaoBddHelper();
		}
		return instance;
	}

	public EntityManager getEm() {
		return this.em;
	}

	public void beginTransaction() {
		final EntityTransaction trans = this.em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
	}

	public void commitTransaction() {
		final EntityTransaction trans = this.em.getTransaction();
		if (trans.isActive()) {
			trans.commit();
		}
	}

	public void rollbackTransaction() {
		final EntityTransaction trans = this.em.getTransaction();
		if (trans.isActive()) {
			trans.rollback();
		}
	}

	/**
	 * Factorise persist / merge / remove avec gestion optionnelle de transaction.
	 */
	public void makePersistencyAction(final Object obj, final boolean useTrans, final PersistencyAction action)
			throws PersistenceException {
		try {
			if (useTrans) {
				beginTransaction();
			}
			switch (action) {
			case PERSIST:
				this.em.persist(obj);
				break;
			case MERGE:
				this.em.merge(obj);
				break;
			case REMOVE:
				Object managed = obj;
				if (!this.em.contains(obj)) {
					managed = this.em.merge(obj);
				}
				this.em.remove(managed);
				break;
			default:
				break;
			}
			if (useTrans) {
				commitTransaction();
			}
		} catch (final PersistenceException e) {
			if (useTrans) {
				rollbackTransaction();
			}
			throw e;
		}
	}
}
