package fr.pau.univ.series.dao.impl.bdd;

import java.util.Collections;
import java.util.List;

import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;

/**
 * Implémentation BDD de {@link ISaisonDao}.
 * Méthodes volontairement vides à ce stade du TP.
 */
public class SaisonDaoBdd implements ISaisonDao {

	@Override
	public Saison createSaison(final Saison s) throws DaoException {
		return null;
	}

	@Override
	public Saison readSaison(final int saisonId) throws DaoException {
		return null;
	}

	@Override
	public List<Saison> readAllSaisons() throws DaoException {
		return Collections.emptyList();
	}

	@Override
	public List<Saison> readSaisonsBySerie(final int serieId) throws DaoException {
		return Collections.emptyList();
	}

	@Override
	public void updateSaison(final Saison s) throws DaoException {
		// à implémenter
	}

	@Override
	public void deleteSaison(final Saison s) throws DaoException {
		// à implémenter
	}
}
