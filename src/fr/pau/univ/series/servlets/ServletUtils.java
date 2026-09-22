package fr.pau.univ.series.servlets;

import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Utilitaires communs aux servlets (redirects, flash messages, rollback).
 */
public final class ServletUtils {

	public static final String FLASH_ERROR = "flashErreur";
	public static final String FLASH_SUCCESS = "flashSucces";

	private ServletUtils() {
	}

	public static void redirectListe(final HttpServletRequest request, final HttpServletResponse response)
			throws java.io.IOException {
		redirectListe(request, response, null);
	}

	/**
	 * Redirige vers la liste, avec ancre optionnelle (ex. {@code ep-12}) pour rester près de l'élément modifié.
	 */
	public static void redirectListe(final HttpServletRequest request, final HttpServletResponse response,
			final String fragment) throws java.io.IOException {
		final StringBuilder url = new StringBuilder(request.getContextPath()).append("/liste");
		if (fragment != null && !fragment.isBlank()) {
			final String safe = fragment.replaceAll("[^a-zA-Z0-9_-]", "");
			if (!safe.isEmpty()) {
				url.append('#').append(safe);
			}
		}
		response.sendRedirect(url.toString());
	}

	public static void setFlashError(final HttpServletRequest request, final String message) {
		request.getSession(true).setAttribute(FLASH_ERROR, message);
	}

	public static void setFlashSuccess(final HttpServletRequest request, final String message) {
		request.getSession(true).setAttribute(FLASH_SUCCESS, message);
	}

	/**
	 * Transfère les messages flash de la session vers la requête, puis les efface.
	 */
	public static void consumeFlash(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		final Object err = session.getAttribute(FLASH_ERROR);
		if (err != null) {
			request.setAttribute("erreur", err);
			session.removeAttribute(FLASH_ERROR);
		}
		final Object ok = session.getAttribute(FLASH_SUCCESS);
		if (ok != null) {
			request.setAttribute("succes", ok);
			session.removeAttribute(FLASH_SUCCESS);
		}
	}

	public static void rollbackQuietly() {
		try {
			DaoBddHelper.getInstance().rollbackTransaction();
		} catch (final DaoException ignored) {
			// ignore
		}
	}

	public static Integer parseId(final String raw) {
		if (raw == null || raw.isBlank()) {
			return null;
		}
		try {
			return Integer.valueOf(raw.trim());
		} catch (final NumberFormatException e) {
			return null;
		}
	}
}
