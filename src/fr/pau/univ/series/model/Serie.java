package fr.pau.univ.series.model;

import java.util.ArrayList;
import java.util.List;

import fr.pau.univ.series.exception.SeriesException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Serie")
@NamedQueries({
		@NamedQuery(name = "Serie.findAll",
				query = "SELECT DISTINCT s FROM Serie s LEFT JOIN FETCH s.saisons sa LEFT JOIN FETCH sa.episodes"),
		@NamedQuery(name = "Serie.findById",
				query = "SELECT DISTINCT s FROM Serie s LEFT JOIN FETCH s.saisons sa LEFT JOIN FETCH sa.episodes WHERE s.id = :id"),
		@NamedQuery(name = "Serie.searchByNom",
				query = "SELECT DISTINCT s FROM Serie s LEFT JOIN FETCH s.saisons sa LEFT JOIN FETCH sa.episodes WHERE LOWER(s.nom) LIKE LOWER(CONCAT('%', :q, '%'))")
})
public class Serie {

	private int id;
	private String nom;
	private boolean toutVu = false;
	private List<Saison> saisons = new ArrayList<>();

	public Serie() {
	}

	public Serie(final String nom) {
		this.nom = nom;
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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

	@Transient
	public int getNbEpisodes() {
		int total = 0;
		for (final Saison s : this.saisons) {
			total += s.getEpisodes().size();
		}
		return total;
	}

	@Transient
	public int getNbEpisodesVus() {
		int vus = 0;
		for (final Saison s : this.saisons) {
			for (final Episode e : s.getEpisodes()) {
				if (e.isVu()) {
					vus++;
				}
			}
		}
		return vus;
	}

	/** Progression 0–100. */
	@Transient
	public int getProgression() {
		final int total = getNbEpisodes();
		if (total == 0) {
			return 0;
		}
		return (100 * getNbEpisodesVus()) / total;
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
