package fr.pau.univ.series.dao.interfaces;

import java.util.List;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;

public interface ISaisonDao {

	Saison createSaison(Saison s, boolean useTransaction) throws DaoException;

	Saison readSaison(int saisonId) throws DaoException;

	List<Saison> readAllSaisons() throws DaoException;

	List<Saison> readSaisonsBySerie(int serieId) throws DaoException;

	void updateSaison(Saison s, boolean useTransaction) throws DaoException;

	void deleteSaison(Saison s, boolean useTransaction) throws DaoException;
}
