package fr.pau.univ.series.dao.impl.bdd;

import fr.pau.univ.series.dao.SeriesContextListener;
import fr.pau.univ.series.exception.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

/**
 * Accès à l'EntityManager du thread courant (un EM par requête HTTP).
 */
public class DaoBddHelper {

	private static final DaoBddHelper INSTANCE = new DaoBddHelper();

	private DaoBddHelper() {
	}

	public static DaoBddHelper getInstance() throws DaoException {
		if (SeriesContextListener.getCurrentEntityManager() == null) {
			throw new DaoException(
					"Aucun EntityManager lié au thread : vérifier EntityManagerFilter / démarrage JPA.");
		}
		return INSTANCE;
	}

	public EntityManager getEm() {
		return SeriesContextListener.getCurrentEntityManager();
	}

	public void beginTransaction() {
		final EntityTransaction trans = getEm().getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
	}

	public void commitTransaction() {
		final EntityTransaction trans = getEm().getTransaction();
		if (trans.isActive()) {
			trans.commit();
		}
	}

	public void rollbackTransaction() {
		final EntityManager em = getEm();
		if (em == null) {
			return;
		}
		final EntityTransaction trans = em.getTransaction();
		if (trans.isActive()) {
			trans.rollback();
		}
	}

	public void makePersistencyAction(final Object obj, final boolean useTrans, final PersistencyAction action)
			throws PersistenceException {
		final EntityManager em = getEm();
		try {
			if (useTrans) {
				beginTransaction();
			}
			switch (action) {
			case PERSIST:
				em.persist(obj);
				break;
			case MERGE:
				em.merge(obj);
				break;
			case REMOVE:
				Object managed = obj;
				if (!em.contains(obj)) {
					managed = em.merge(obj);
				}
				em.remove(managed);
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
