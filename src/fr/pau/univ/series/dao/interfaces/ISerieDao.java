package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

public interface ISerieDao {

	/**
	 * Crée et persiste une série.
	 *
	 * @param s              série à créer
	 * @param useTransaction {@code true} pour ouvrir/commit une transaction ici
	 * @return la série créée
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	Serie createSerie(Serie s, boolean useTransaction) throws DaoException;

	Serie readSerie(int serieId) throws DaoException;

	List<Serie> readAllSeries() throws DaoException;

	void updateSerie(Serie s, boolean useTransaction) throws DaoException;

	void deleteSerie(Serie s, boolean useTransaction) throws DaoException;
}
