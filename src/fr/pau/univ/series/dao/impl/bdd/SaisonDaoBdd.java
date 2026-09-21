package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class SaisonDaoBdd implements ISaisonDao {

	private final DaoBddHelper bdd;

	public SaisonDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Saison createSaison(final Saison s, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(s, useTransaction, PersistencyAction.PERSIST);
			return s;
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de créer la saison", e);
		}
	}

	@Override
	public Saison readSaison(final int saisonId) throws DaoException {
		try {
			final TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findById", Saison.class);
			query.setParameter("id", saisonId);
			final List<Saison> ret = query.getResultList();
			if (!ret.isEmpty()) {
				return ret.get(0);
			}
			return null;
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lire la saison " + saisonId, e);
		}
	}

	@Override
	public List<Saison> readAllSaisons() throws DaoException {
		try {
			return this.bdd.getEm().createNamedQuery("Saison.findAll", Saison.class).getResultList();
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lister les saisons", e);
		}
	}

	@Override
	public List<Saison> readSaisonsBySerie(final int serieId) throws DaoException {
		try {
			final TypedQuery<Saison> query = this.bdd.getEm().createNamedQuery("Saison.findBySerie", Saison.class);
			query.setParameter("id", serieId);
			return query.getResultList();
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lire les saisons de la série " + serieId, e);
		}
	}

	@Override
	public void updateSaison(final Saison s, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(s, useTransaction, PersistencyAction.MERGE);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de modifier la saison", e);
		}
	}

	@Override
	public void deleteSaison(final Saison s, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(s, useTransaction, PersistencyAction.REMOVE);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de supprimer la saison", e);
		}
	}
}
