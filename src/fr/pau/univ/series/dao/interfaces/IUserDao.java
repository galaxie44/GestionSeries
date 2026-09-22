package fr.pau.univ.series.dao.interfaces;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.User;

public interface IUserDao {

	User createUser(User user, boolean useTransaction) throws DaoException;

	User readUser(int id) throws DaoException;

	User readUserByLogin(String login) throws DaoException;

	void updateUser(User user, boolean useTransaction) throws DaoException;

	void deleteUser(User user, boolean useTransaction) throws DaoException;
}
