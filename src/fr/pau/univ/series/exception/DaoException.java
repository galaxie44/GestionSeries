package fr.pau.univ.series.exception;

/**
 * Exception encapsulant les erreurs de la couche DAO.
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message message d'erreur
	 */
	public DaoException(final String message) {
		super(message);
	}

	/**
	 * @param cause cause de l'erreur
	 */
	public DaoException(final Throwable cause) {
		super(cause);
	}

	/**
	 * @param message message d'erreur
	 * @param cause   cause de l'erreur
	 */
	public DaoException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
