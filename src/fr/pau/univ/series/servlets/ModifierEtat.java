package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.model.Episode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/etat")
public class ModifierEtat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		String anchor = null;
		try {
			final Integer episodeId = ServletUtils.parseId(request.getParameter("episode"));
			if (episodeId == null) {
				ServletUtils.setFlashError(request, "Identifiant d'épisode invalide.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			anchor = "ep-" + episodeId;
			final Episode e = DaoFactory.getInstance().getEpisodeDao().readEpisode(episodeId);
			if (e != null) {
				e.setVu(!e.isVu());
				DaoFactory.getInstance().getEpisodeDao().updateEpisode(e, true);
				ServletUtils.setFlashSuccess(request, e.isVu() ? "Épisode marqué comme vu." : "Épisode marqué à voir.");
			} else {
				ServletUtils.setFlashError(request, "Épisode introuvable.");
			}
		} catch (final Exception e) {
			ServletUtils.setFlashError(request, e.getMessage());
			e.printStackTrace();
		}
		ServletUtils.redirectListe(request, response, anchor);
	}
}
