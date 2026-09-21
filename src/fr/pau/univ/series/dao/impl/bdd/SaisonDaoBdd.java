package fr.pau.univ.series.dao.impl.bdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.pau.univ.series.dao.interfaces.ISaisonDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.exception.SeriesException;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;

/**
 * Implémentation temporaire en mémoire de {@link ISaisonDao}.
 */
public class SaisonDaoBdd implements ISaisonDao {

	private final MemoireStore store = MemoireStore.getInstance();

	@Override
	public Saison createSaison(final Saison s) throws DaoException {
		return s;
	}

	@Override
	public Saison readSaison(final int saisonId) throws DaoException {
		for (final Serie serie : this.store.getSeries()) {
			for (final Saison saison : serie.getSaisons()) {
				if (saison.getId() == saisonId) {
					return saison;
				}
			}
		}
		return null;
	}

	@Override
	public List<Saison> readAllSaisons() throws DaoException {
		final List<Saison> all = new ArrayList<>();
		for (final Serie serie : this.store.getSeries()) {
			all.addAll(serie.getSaisons());
		}
		return all;
	}

	@Override
	public List<Saison> readSaisonsBySerie(final int serieId) throws DaoException {
		for (final Serie serie : this.store.getSeries()) {
			if (serie.getId() == serieId) {
				return new ArrayList<>(serie.getSaisons());
			}
		}
		return Collections.emptyList();
	}

	@Override
	public void updateSaison(final Saison s) throws DaoException {
		// Objet déjà en mémoire
	}

	@Override
	public void deleteSaison(final Saison s) throws DaoException {
		for (final Serie serie : this.store.getSeries()) {
			try {
				serie.removeSaison(s);
				return;
			} catch (final SeriesException ignored) {
				// saison absente de cette série
			}
		}
	}
}
