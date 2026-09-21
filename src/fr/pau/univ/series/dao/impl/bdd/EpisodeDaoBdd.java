package fr.pau.univ.series.dao.impl.bdd;

import java.util.Collections;
import java.util.List;

import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;

/**
 * Implémentation BDD de {@link IEpisodeDao}.
 * Méthodes volontairement vides à ce stade du TP.
 */
public class EpisodeDaoBdd implements IEpisodeDao {

	@Override
	public Episode createEpisode(final Episode e) throws DaoException {
		return null;
	}

	@Override
	public Episode readEpisode(final int episodeId) throws DaoException {
		return null;
	}

	@Override
	public List<Episode> readAllEpisodes() throws DaoException {
		return Collections.emptyList();
	}

	@Override
	public List<Episode> readEpisodesBySaison(final int saisonId) throws DaoException {
		return Collections.emptyList();
	}

	@Override
	public void updateEpisode(final Episode e) throws DaoException {
		// à implémenter
	}

	@Override
	public void deleteEpisode(final Episode e) throws DaoException {
		// à implémenter
	}
}
