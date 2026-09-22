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
@Table(name = "Saison")
@NamedQueries({
		@NamedQuery(name = "Saison.findById",
				query = "SELECT DISTINCT s FROM Saison s LEFT JOIN FETCH s.episodes WHERE s.id = :id"),
		@NamedQuery(name = "Saison.findAll", query = "SELECT s FROM Saison s"),
		@NamedQuery(name = "Saison.findByEpisode",
				query = "SELECT s FROM Saison s JOIN s.episodes e WHERE e.id = :id"),
		@NamedQuery(name = "Saison.findBySerie",
				query = "SELECT sais FROM Serie ser JOIN ser.saisons sais WHERE ser.id = :id")
})
public class Saison {

	private int id;
	private int numero;
	private String nom;
	private boolean toutVu = false;
	private List<Episode> episodes = new ArrayList<>();

	public Saison() {
	}

	public Saison(final String nom, final int numero) {
		this.nom = nom;
		this.numero = numero;
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

	@Column(name = "numero", nullable = false)
	public int getNumero() {
		return this.numero;
	}

	public void setNumero(final int numero) {
		this.numero = numero;
	}

	@Column(name = "nom", nullable = false)
	public String getNom() {
		return this.nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_saison", referencedColumnName = "id")
	public List<Episode> getEpisodes() {
		return this.episodes;
	}

	public void setEpisodes(final List<Episode> episodes) {
		this.episodes = episodes;
	}

	public Episode addEpisode(final Episode e) throws SeriesException {
		this.episodes.add(e);
		return e;
	}

	public void removeEpisode(final Episode e) throws SeriesException {
		if (!this.episodes.remove(e)) {
			throw new SeriesException("Aucun épisode trouvée avec l'identifiant ".concat(Integer.toString(e.getId())));
		}
	}

	public void setToutVu(final boolean toutVu) {
		this.toutVu = toutVu;
	}

	@Transient
	public boolean isToutVu() {
		this.toutVu = true;
		if (!this.episodes.isEmpty()) {
			for (final Episode e : this.episodes) {
				if (!e.isVu()) {
					this.toutVu = false;
					return this.toutVu;
				}
			}
		}
		return this.toutVu;
	}

	public Episode getEpisodeById(final int id) throws SeriesException {
		for (final Episode ep : this.episodes) {
			if (ep.getId() == id) {
				return ep;
			}
		}
		throw new SeriesException("Aucun épisode trouvé avec l'identifiant ".concat(Integer.toString(id)));
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
		if (!(obj instanceof Saison)) {
			return false;
		}
		return ((Saison) obj).getId() == getId();
	}
}
