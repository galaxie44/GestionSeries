package fr.pau.univ.series.dao.impl.bdd;

import java.util.Collections;
import java.util.List;

import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

/**
 * Implémentation BDD de {@link ISerieDao}.
 * Méthodes volontairement vides à ce stade du TP.
 */
public class SerieDaoBdd implements ISerieDao {

	@Override
	public Serie createSerie(final Serie s) throws DaoException {
		return null;
	}

	@Override
	public Serie readSerie(final int serieId) throws DaoException {
		return null;
	}

	@Override
	public List<Serie> readAllSeries() throws DaoException {
		return Collections.emptyList();
	}

	@Override
	public void updateSerie(final Serie s) throws DaoException {
		// à implémenter
	}

	@Override
	public void deleteSerie(final Serie s) throws DaoException {
		// à implémenter
	}
}
