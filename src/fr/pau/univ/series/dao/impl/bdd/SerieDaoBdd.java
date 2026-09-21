package fr.pau.univ.series.dao.impl.bdd;

import java.util.ArrayList;
import java.util.List;

import fr.pau.univ.series.dao.interfaces.ISerieDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;

/**
 * Implémentation BDD de {@link ISerieDao}.
 * Pour l'instant : lecture/écriture via {@link MemoireStore} ;
 * {@link DaoBddHelper} est déjà branché pour les futures opérations JPA.
 */
public class SerieDaoBdd implements ISerieDao {

	private final DaoBddHelper bdd;
	private final MemoireStore store = MemoireStore.getInstance();

	public SerieDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

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
		// Objet déjà en mémoire ; JPA merge viendra plus tard
	}

	@Override
	public void deleteSerie(final Serie s) throws DaoException {
		this.store.getSeries().remove(s);
	}

	/** Accès au helper JPA (transactions, EntityManager). */
	protected DaoBddHelper getBdd() {
		return this.bdd;
	}
}
