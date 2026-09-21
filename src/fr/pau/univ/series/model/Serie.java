package fr.pau.univ.series.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.SeriesException;

public class Serie {

	private int id;
	private final String nom;
	private boolean toutVu = false;
	private final List<Saison> saisons = new ArrayList<>();

	/**
	 * @param nom Nom de la série.
	 */
	public Serie(final String nom) {
		this.nom = nom;
		setId(++DaoFactory.lastSerieId);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the nom
	 */
	public final String getNom() {
		return this.nom;
	}

	/**
	 * @return the saisons
	 */
	public final Collection<Saison> getSaisons() {
		return this.saisons;
	}

	/**
	 * This method adds given Saison to the list of Saisons.
	 *
	 * @param s The Saison to add. <b>note :</b>given Saison id is calculated before
	 *          storing. No need to set it before calling this method.
	 * @return the added Saison
	 * @throws SeriesException if a Saison already exist with same id.
	 */
	public Saison addSaison(final Saison s) throws SeriesException {
		this.saisons.add(s);
		return s;
	}

	/**
	 * This method removes given Saison from the list of Saisons.
	 *
	 * @param s the Saison to remove.
	 * @throws SeriesException if given Saison doesn't exist in the list of Saisons.
	 */
	public void removeSaison(final Saison s) throws SeriesException {
		if (this.saisons.get(s.getId()) == null) {
			throw new SeriesException("Aucune saison trouvée avec l'identifiant ".concat(Integer.toString(s.getId())));
		}
		this.saisons.remove(s.getId());
	}

	/**
	 * @param toutVu the toutVu to set
	 */
	public final void setToutVu(final boolean toutVu) {
		this.toutVu = toutVu;
	}

	/**
	 *
	 * This method returns the visualization state of the Serie.
	 *
	 * @return <code>true</code> if all Saisons of the Serie have been fully
	 *         watched, <code>false</code> otherwise.
	 */
	public boolean isToutVu() {
		this.toutVu = true;
		if (this.saisons.size() > 0) {
			for (final Saison s : this.saisons) {
				if (!s.isToutVu()) {
					this.toutVu = false;
					return this.toutVu;
				}
			}
		}
		return this.toutVu;
	}

	/**
	 * This method return the Saison with given id.
	 *
	 * @param id Identifier of Saison to return
	 * @return the Saison with given id or <code>null</code> if no Saison exists
	 *         with given Id.
	 * @throws SeriesException in case season was not found.
	 */
	public Saison getSaisonById(final int id) throws SeriesException {
		for (final Saison saison : this.saisons) {
			if (saison.getId() == id) {
				return saison;
			}
		}
		throw new SeriesException("Aucune saison trouvée avec l'identifiant ".concat(Integer.toString(id)));
	}

	@Override
	public String toString() {
		return getNom();
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Serie)) {
			return false;
		}
		return ((Serie) obj).getId() == getId();
	}

}
