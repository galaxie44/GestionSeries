package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class SerieDaoBdd implements ISerieDao {

	private final DaoBddHelper bdd;

	public SerieDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Serie createSerie(final Serie s, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(s, useTransaction, PersistencyAction.PERSIST);
			return s;
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de créer la série", e);
		}
	}

	@Override
	public Serie readSerie(final int serieId) throws DaoException {
		try {
			final TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findById", Serie.class);
			query.setParameter("id", serieId);
			final List<Serie> ret = query.getResultList();
			if (!ret.isEmpty()) {
				return ret.get(0);
			}
			return null;
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lire la série " + serieId, e);
		}
	}

	@Override
	public List<Serie> readAllSeries() throws DaoException {
		try {
			final TypedQuery<Serie> query = this.bdd.getEm().createNamedQuery("Serie.findAll", Serie.class);
			return query.getResultList();
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lister les séries", e);
		}
	}

	@Override
	public void updateSerie(final Serie s, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(s, useTransaction, PersistencyAction.MERGE);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de modifier la série", e);
		}
	}

	@Override
	public void deleteSerie(final Serie s, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(s, useTransaction, PersistencyAction.REMOVE);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de supprimer la série", e);
		}
	}
}
