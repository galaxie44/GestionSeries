package fr.pau.univ.series.dao.impl.bdd;

import java.util.ArrayList;
import java.util.List;

import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

/**
 * Implémentation temporaire en mémoire de {@link ISerieDao}
 * (remplacée plus tard par JPA / BDD).
 */
public class SerieDaoBdd implements ISerieDao {

	private final MemoireStore store = MemoireStore.getInstance();

	@Override
	public Serie createSerie(final Serie s) throws DaoException {
		this.store.getSeries().add(s);
		return s;
	}

	@Override
	public Serie readSerie(final int serieId) throws DaoException {
		for (final Serie serie : this.store.getSeries()) {
			if (serie.getId() == serieId) {
				return serie;
			}
		}
		return null;
	}

	@Override
	public List<Serie> readAllSeries() throws DaoException {
		return new ArrayList<>(this.store.getSeries());
	}

	@Override
	public void updateSerie(final Serie s) throws DaoException {
		// Objet déjà en mémoire : rien à faire pour l'instant
	}

	@Override
	public void deleteSerie(final Serie s) throws DaoException {
		this.store.getSeries().remove(s);
	}
}
