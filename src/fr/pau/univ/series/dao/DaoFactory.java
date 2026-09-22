package fr.pau.univ.series.dao;

import fr.pau.univ.series.dao.impl.bdd.EpisodeDaoBdd;
import fr.pau.univ.series.dao.impl.bdd.SaisonDaoBdd;
import fr.pau.univ.series.dao.impl.bdd.SerieDaoBdd;
import fr.pau.univ.series.dao.impl.bdd.UserDaoBdd;
import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.dao.interfaces.IUserDao;
import fr.pau.univ.series.exception.DaoException;

/**
 * Singleton et Factory chargée de fournir les DAO.
 */
public class DaoFactory {

	private static DaoFactory instance;

	private ISerieDao serieDao = null;
	private ISaisonDao saisonDao = null;
	private IEpisodeDao episodeDao = null;
	private IUserDao userDao = null;

	public static final DaoFactory getInstance() throws DaoException {
		if (instance == null) {
			instance = new DaoFactory();
		}
		return instance;
	}

	private DaoFactory() throws DaoException {
	}

	public ISerieDao getSerieDao() throws DaoException {
		if (this.serieDao == null) {
			this.serieDao = new SerieDaoBdd();
		}
		return this.serieDao;
	}

	public ISaisonDao getSaisonDao() throws DaoException {
		if (this.saisonDao == null) {
			this.saisonDao = new SaisonDaoBdd();
		}
		return this.saisonDao;
	}

	public IEpisodeDao getEpisodeDao() throws DaoException {
		if (this.episodeDao == null) {
			this.episodeDao = new EpisodeDaoBdd();
		}
		return this.episodeDao;
	}

	public IUserDao getUserDao() throws DaoException {
		if (this.userDao == null) {
			this.userDao = new UserDaoBdd();
		}
		return this.userDao;
	}
}
