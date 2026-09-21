package fr.pau.univ.series.dao;

import fr.pau.univ.series.dao.impl.bdd.EpisodeDaoBdd;
import fr.pau.univ.series.dao.impl.bdd.SaisonDaoBdd;
import fr.pau.univ.series.dao.impl.bdd.SerieDaoBdd;
import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;

/**
 * Singleton et Factory chargée de fournir les DAO.
 */
public class DaoFactory {

	private static DaoFactory instance;

	/** Compteurs temporaires d'identifiants (avant génération JPA). */
	public static int lastSerieId = 0;
	public static int lastSaisonId = 0;
	public static int lastEpisodeId = 0;

	private ISerieDao serieDao = null;
	private ISaisonDao saisonDao = null;
	private IEpisodeDao episodeDao = null;

	/**
	 * Retourne l'unique instance de {@link DaoFactory} (lazy loading).
	 *
	 * @return l'instance du singleton
	 * @throws DaoException si l'instanciation échoue
	 */
	public static final DaoFactory getInstance() throws DaoException {
		if (instance == null) {
			instance = new DaoFactory();
		}
		return instance;
	}

	/**
	 * Constructeur privé : empêche toute instanciation externe (Singleton).
	 *
	 * @throws DaoException si l'initialisation échoue
	 */
	private DaoFactory() throws DaoException {
	}

	/**
	 * Fournit le DAO des séries (lazy loading).
	 *
	 * @return l'implémentation {@link ISerieDao}
	 */
	public ISerieDao getSerieDao() {
		if (this.serieDao == null) {
			this.serieDao = new SerieDaoBdd();
		}
		return this.serieDao;
	}

	/**
	 * Fournit le DAO des saisons (lazy loading).
	 *
	 * @return l'implémentation {@link ISaisonDao}
	 */
	public ISaisonDao getSaisonDao() {
		if (this.saisonDao == null) {
			this.saisonDao = new SaisonDaoBdd();
		}
		return this.saisonDao;
	}

	/**
	 * Fournit le DAO des épisodes (lazy loading).
	 *
	 * @return l'implémentation {@link IEpisodeDao}
	 */
	public IEpisodeDao getEpisodeDao() {
		if (this.episodeDao == null) {
			this.episodeDao = new EpisodeDaoBdd();
		}
		return this.episodeDao;
	}
}
