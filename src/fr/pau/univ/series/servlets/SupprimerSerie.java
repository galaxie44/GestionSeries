package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.model.Serie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/supprimerSerie")
public class SupprimerSerie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final Integer serieId = ServletUtils.parseId(request.getParameter("serie"));
			if (serieId == null) {
				ServletUtils.setFlashError(request, "Identifiant de série invalide.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			final Serie serie = DaoFactory.getInstance().getSerieDao().readSerie(serieId);
			if (serie != null) {
				DaoFactory.getInstance().getSerieDao().deleteSerie(serie, true);
				ServletUtils.setFlashSuccess(request, "Série supprimée.");
			} else {
				ServletUtils.setFlashError(request, "Série introuvable.");
			}
		} catch (final Exception e) {
			ServletUtils.setFlashError(request, e.getMessage());
			e.printStackTrace();
		}
		ServletUtils.redirectListe(request, response);
	}
}
