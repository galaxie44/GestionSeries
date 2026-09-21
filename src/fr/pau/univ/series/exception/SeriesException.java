package fr.pau.univ.series.exception;

public class SeriesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeriesException(String message) {
		super(message);
	}

	public SeriesException(Throwable cause) {
		super(cause);
	}

	public SeriesException(String message, Throwable cause) {
		super(message, cause);
	}

}
