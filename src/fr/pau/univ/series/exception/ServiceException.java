package fr.pau.univ.series.exception;

/**
 * Erreur métier / sécurité côté services REST.
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(final String message) {
		super(message);
	}

	public ServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
