package fr.pau.univ.series.model;

import java.util.ArrayList;
import java.util.List;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.SeriesException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Entité JPA représentant une série TV.
 * Les annotations indiquent à JPA comment mapper l'objet vers la table BDD.
 */
@Entity
@Table(name = "Serie")
public class Serie {

	private int id;
	private String nom;
	private boolean toutVu = false;
	private List<Saison> saisons = new ArrayList<>();

	/** Constructeur sans paramètre obligatoire pour JPA. */
	public Serie() {
	}

	/**
	 * @param nom Nom de la série.
	 */
	public Serie(final String nom) {
		this.nom = nom;
		setId(++DaoFactory.lastSerieId);
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Column(name = "nom", nullable = false)
	public String getNom() {
		return this.nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	/**
	 * Relation 1-N : une série possède plusieurs saisons.
	 * cascade=ALL : créer/modifier/supprimer une série propage aux saisons.
	 * orphanRemoval : une saison retirée de la liste est effacée en BDD.
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_serie", referencedColumnName = "id")
	public List<Saison> getSaisons() {
		return this.saisons;
	}

	public void setSaisons(final List<Saison> saisons) {
		this.saisons = saisons;
	}

	public Saison addSaison(final Saison s) throws SeriesException {
		this.saisons.add(s);
		return s;
	}

	public void removeSaison(final Saison s) throws SeriesException {
		if (!this.saisons.remove(s)) {
			throw new SeriesException("Aucune saison trouvée avec l'identifiant ".concat(Integer.toString(s.getId())));
		}
	}

	public void setToutVu(final boolean toutVu) {
		this.toutVu = toutVu;
	}

	/**
	 * Calculé à la volée : non stocké en BDD.
	 */
	@Transient
	public boolean isToutVu() {
		this.toutVu = true;
		if (!this.saisons.isEmpty()) {
			for (final Saison s : this.saisons) {
				if (!s.isToutVu()) {
					this.toutVu = false;
					return this.toutVu;
				}
			}
		}
		return this.toutVu;
	}

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
