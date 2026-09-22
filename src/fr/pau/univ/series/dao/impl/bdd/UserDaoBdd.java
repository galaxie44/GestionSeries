package fr.pau.univ.series.dao.impl.bdd;

import java.util.List;

import fr.pau.univ.series.dao.interfaces.IUserDao;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.User;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class UserDaoBdd implements IUserDao {

	private final DaoBddHelper bdd;

	public UserDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public User createUser(final User user, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(user, useTransaction, PersistencyAction.PERSIST);
			return user;
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de créer l'utilisateur", e);
		}
	}

	@Override
	public User readUser(final int id) throws DaoException {
		try {
			final TypedQuery<User> query = this.bdd.getEm().createNamedQuery("User.findById", User.class);
			query.setParameter("id", id);
			final List<User> ret = query.getResultList();
			return ret.isEmpty() ? null : ret.get(0);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lire l'utilisateur " + id, e);
		}
	}

	@Override
	public User readUserByLogin(final String login) throws DaoException {
		try {
			final TypedQuery<User> query = this.bdd.getEm().createNamedQuery("User.findByLogin", User.class);
			query.setParameter("login", login);
			final List<User> ret = query.getResultList();
			return ret.isEmpty() ? null : ret.get(0);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de lire l'utilisateur " + login, e);
		}
	}

	@Override
	public void updateUser(final User user, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(user, useTransaction, PersistencyAction.MERGE);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de modifier l'utilisateur", e);
		}
	}

	@Override
	public void deleteUser(final User user, final boolean useTransaction) throws DaoException {
		try {
			this.bdd.makePersistencyAction(user, useTransaction, PersistencyAction.REMOVE);
		} catch (final PersistenceException e) {
			throw new DaoException("Impossible de supprimer l'utilisateur", e);
		}
	}
}
