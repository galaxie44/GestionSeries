package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;

public interface IEpisodeDao {

	Episode createEpisode(Episode e, boolean useTransaction) throws DaoException;

	Episode readEpisode(int episodeId) throws DaoException;

	List<Episode> readAllEpisodes() throws DaoException;

	List<Episode> readEpisodesBySaison(int saisonId) throws DaoException;

	void updateEpisode(Episode e, boolean useTransaction) throws DaoException;

	void deleteEpisode(Episode e, boolean useTransaction) throws DaoException;
}
