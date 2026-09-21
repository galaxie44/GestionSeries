package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;

/**
 * Contrat d'accès aux données pour les épisodes (CRUD).
 */
public interface IEpisodeDao {

	/**
	 * Crée et persiste un épisode.
	 *
	 * @param e l'épisode à créer
	 * @return l'épisode créé
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	Episode createEpisode(Episode e) throws DaoException;

	/**
	 * Lit un épisode à partir de son identifiant.
	 *
	 * @param episodeId identifiant de l'épisode
	 * @return l'épisode trouvé, ou {@code null} si absent
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	Episode readEpisode(int episodeId) throws DaoException;

	/**
	 * Lit tous les épisodes.
	 *
	 * @return la liste de tous les épisodes
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	List<Episode> readAllEpisodes() throws DaoException;

	/**
	 * Lit les épisodes d'une saison donnée.
	 *
	 * @param saisonId identifiant de la saison
	 * @return les épisodes de la saison
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	List<Episode> readEpisodesBySaison(int saisonId) throws DaoException;

	/**
	 * Met à jour un épisode existant.
	 *
	 * @param e l'épisode à mettre à jour
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	void updateEpisode(Episode e) throws DaoException;

	/**
	 * Supprime un épisode.
	 *
	 * @param e l'épisode à supprimer
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	void deleteEpisode(Episode e) throws DaoException;
}
