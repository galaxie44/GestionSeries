package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

/**
 * Contrat d'accès aux données pour les séries (CRUD).
 */
public interface ISerieDao {

	/**
	 * Crée et persiste une série.
	 *
	 * @param s la série à créer
	 * @return la série créée
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	Serie createSerie(Serie s) throws DaoException;

	/**
	 * Lit une série à partir de son identifiant.
	 *
	 * @param serieId identifiant de la série
	 * @return la série trouvée, ou {@code null} si absente
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	Serie readSerie(int serieId) throws DaoException;

	/**
	 * Lit toutes les séries.
	 *
	 * @return la liste de toutes les séries
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	List<Serie> readAllSeries() throws DaoException;

	/**
	 * Met à jour une série existante.
	 *
	 * @param s la série à mettre à jour
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	void updateSerie(Serie s) throws DaoException;

	/**
	 * Supprime une série.
	 *
	 * @param s la série à supprimer
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	void deleteSerie(Serie s) throws DaoException;
}
