package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/creerEpisode")
public class CreerEpisode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final Integer idSaison = ServletUtils.parseId(request.getParameter("saison"));
			final String nomEpisode = request.getParameter("nomEpisode");
			if (idSaison == null) {
				ServletUtils.setFlashError(request, "Identifiant de saison invalide.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			if (nomEpisode != null && !nomEpisode.isBlank()) {
				final Saison sais = DaoFactory.getInstance().getSaisonDao().readSaison(idSaison);
				if (sais != null) {
					final Episode ep = new Episode(nomEpisode.trim(), sais.getEpisodes().size() + 1);
					DaoBddHelper.getInstance().beginTransaction();
					DaoFactory.getInstance().getEpisodeDao().createEpisode(ep, false);
					sais.addEpisode(ep);
					DaoFactory.getInstance().getSaisonDao().updateSaison(sais, false);
					DaoBddHelper.getInstance().commitTransaction();
					ServletUtils.setFlashSuccess(request, "Épisode ajouté.");
				} else {
					ServletUtils.setFlashError(request, "Saison introuvable.");
				}
			} else {
				ServletUtils.setFlashError(request, "Le titre de l'épisode est obligatoire.");
			}
		} catch (final Exception e) {
			ServletUtils.rollbackQuietly();
			ServletUtils.setFlashError(request, e.getMessage());
			e.printStackTrace();
		}
		ServletUtils.redirectListe(request, response);
	}
}
