package fr.pau.univ.series.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "AppUser")
@NamedQueries({
		@NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
})
public class User {

	private int id;
	private String login;
	private String password;
	private UserRole role = UserRole.ROLE_USER;

	public User() {
	}

	public User(final String login, final String password, final UserRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
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

	@Column(name = "login", nullable = false, unique = true)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false)
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	public UserRole getRole() {
		return this.role;
	}

	public void setRole(final UserRole role) {
		this.role = role;
	}
}
