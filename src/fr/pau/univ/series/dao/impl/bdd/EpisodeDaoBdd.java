package fr.pau.univ.series.dao.impl.bdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.pau.univ.series.dao.interfaces.IEpisodeDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.exception.SeriesException;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;

/**
 * Implémentation temporaire en mémoire de {@link IEpisodeDao}.
 */
public class EpisodeDaoBdd implements IEpisodeDao {

	private final MemoireStore store = MemoireStore.getInstance();

	@Override
	public Episode createEpisode(final Episode e) throws DaoException {
		return e;
	}

	@Override
	public Episode readEpisode(final int episodeId) throws DaoException {
		for (final Serie serie : this.store.getSeries()) {
			for (final Saison saison : serie.getSaisons()) {
				for (final Episode episode : saison.getEpisodes()) {
					if (episode.getId() == episodeId) {
						return episode;
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<Episode> readAllEpisodes() throws DaoException {
		final List<Episode> all = new ArrayList<>();
		for (final Serie serie : this.store.getSeries()) {
			for (final Saison saison : serie.getSaisons()) {
				all.addAll(saison.getEpisodes());
			}
		}
		return all;
	}

	@Override
	public List<Episode> readEpisodesBySaison(final int saisonId) throws DaoException {
		for (final Serie serie : this.store.getSeries()) {
			for (final Saison saison : serie.getSaisons()) {
				if (saison.getId() == saisonId) {
					return new ArrayList<>(saison.getEpisodes());
				}
			}
		}
		return Collections.emptyList();
	}

	@Override
	public void updateEpisode(final Episode e) throws DaoException {
		// Objet déjà en mémoire (ex. bascule vu / non vu)
	}

	@Override
	public void deleteEpisode(final Episode e) throws DaoException {
		for (final Serie serie : this.store.getSeries()) {
			for (final Saison saison : serie.getSaisons()) {
				try {
					saison.removeEpisode(e);
					return;
				} catch (final SeriesException ignored) {
					// épisode absent de cette saison
				}
			}
		}
	}
}
