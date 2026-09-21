package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class EpisodeDaoBdd implements IEpisodeDao {

	private final DaoBddHelper bdd;

	public EpisodeDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Episode createEpisode(final Episode e, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(e, useTransaction, PersistencyAction.PERSIST);
			return e;
		} catch (final PersistenceException e1) {
			throw new DaoException("Impossible de créer l'épisode", e1);
		}
	}

	@Override
	public Episode readEpisode(final int episodeId) throws DaoException {
		try {
			final TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findById", Episode.class);
			query.setParameter("id", episodeId);
			final List<Episode> ret = query.getResultList();
			if (!ret.isEmpty()) {
				return ret.get(0);
			}
			return null;
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lire l'épisode " + episodeId, e);
		}
	}

	@Override
	public List<Episode> readAllEpisodes() throws DaoException {
		try {
			return this.bdd.getEm().createNamedQuery("Episode.findAll", Episode.class).getResultList();
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lister les épisodes", e);
		}
	}

	@Override
	public List<Episode> readEpisodesBySaison(final int saisonId) throws DaoException {
		try {
			final TypedQuery<Episode> query = this.bdd.getEm().createNamedQuery("Episode.findBySaison", Episode.class);
			query.setParameter("id", saisonId);
			return query.getResultList();
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lire les épisodes de la saison " + saisonId, e);
		}
	}

	@Override
	public void updateEpisode(final Episode e, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(e, useTransaction, PersistencyAction.MERGE);
		} catch (final PersistenceException ex) {
			throw new DaoException("Impossible de modifier l'épisode", ex);
		}
	}

	@Override
	public void deleteEpisode(final Episode e, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(e, useTransaction, PersistencyAction.REMOVE);
		} catch (final PersistenceException ex) {
			throw new DaoException("Impossible de supprimer l'épisode", ex);
		}
	}
}
