package fr.pau.univ.series.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Episode")
@NamedQueries({
		@NamedQuery(name = "Episode.findById", query = "SELECT e FROM Episode e WHERE e.id = :id"),
		@NamedQuery(name = "Episode.findAll", query = "SELECT e FROM Episode e"),
		@NamedQuery(name = "Episode.findBySaison",
				query = "SELECT e FROM Saison s JOIN s.episodes e WHERE s.id = :id")
})
public class Episode {

	private int id;
	private String titre;
	private int numero;
	private boolean vu = false;

	public Episode() {
	}

	public Episode(final String titre, final int numero) {
		this.titre = titre;
		this.numero = numero;
	}

	/** Constructeur pratique quand le numéro n'est pas encore connu. */
	public Episode(final String titre) {
		this.titre = titre;
		this.numero = 1;
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

	@Column(name = "titre", nullable = false)
	public String getTitre() {
		return this.titre;
	}

	public void setTitre(final String titre) {
		this.titre = titre;
	}

	@Column(name = "numero", nullable = false)
	public int getNumero() {
		return this.numero;
	}

	public void setNumero(final int numero) {
		this.numero = numero;
	}

	@Column(name = "vu", nullable = false)
	public boolean isVu() {
		return this.vu;
	}

	public void setVu(final boolean vu) {
		this.vu = vu;
	}

	@Override
	public String toString() {
		return getTitre();
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Episode)) {
			return false;
		}
		return ((Episode) obj).getId() == getId();
	}
}
