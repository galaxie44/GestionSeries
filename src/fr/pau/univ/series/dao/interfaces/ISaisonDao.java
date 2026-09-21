package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;

/**
 * Contrat d'accès aux données pour les saisons (CRUD).
 */
public interface ISaisonDao {

	/**
	 * Crée et persiste une saison.
	 *
	 * @param s la saison à créer
	 * @return la saison créée
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	Saison createSaison(Saison s) throws DaoException;

	/**
	 * Lit une saison à partir de son identifiant.
	 *
	 * @param saisonId identifiant de la saison
	 * @return la saison trouvée, ou {@code null} si absente
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	Saison readSaison(int saisonId) throws DaoException;

	/**
	 * Lit toutes les saisons.
	 *
	 * @return la liste de toutes les saisons
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	List<Saison> readAllSaisons() throws DaoException;

	/**
	 * Lit les saisons d'une série donnée.
	 *
	 * @param serieId identifiant de la série
	 * @return les saisons de la série
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	List<Saison> readSaisonsBySerie(int serieId) throws DaoException;

	/**
	 * Met à jour une saison existante.
	 *
	 * @param s la saison à mettre à jour
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	void updateSaison(Saison s) throws DaoException;

	/**
	 * Supprime une saison.
	 *
	 * @param s la saison à supprimer
	 * @throws DaoException en cas d'erreur d'accès aux données
	 */
	void deleteSaison(Saison s) throws DaoException;
}
