package fr.pau.univ.series.model;

import fr.pau.univ.series.dao.DaoFactory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entité JPA représentant un épisode.
 */
@Entity
@Table(name = "Episode")
public class Episode {

	private int id;
	private String titre;
	private int numero;
	private boolean vu = false;

	/** Constructeur sans paramètre obligatoire pour JPA. */
	public Episode() {
	}

	/**
	 * @param titre  titre de l'épisode
	 * @param numero numéro de l'épisode
	 */
	public Episode(final String titre, final int numero) {
		this.titre = titre;
		this.numero = numero;
		setId(++DaoFactory.lastEpisodeId);
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
