package fr.pau.univ.series.services;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

/**
 * API REST épisodes — POST /api/episode/add (PDF p.20, token admin Phase C).
 */
@Path("/episode")
public class EpisodeService {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEpisode(final MultivaluedMap<String, String> formParams) {
		final Response forbidden = AuthenticationService.requireAdmin(formParams);
		if (forbidden != null) {
			return forbidden;
		}
		if (formParams.get("titre") == null || formParams.get("saisonId") == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		}
		final Saison s;
		try {
			s = DaoFactory.getInstance().getSaisonDao()
					.readSaison(Integer.parseInt(formParams.getFirst("saisonId")));
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (final NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("saisonId must be an integer").build();
		}
		if (s == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Parent season not found").build();
		}
		final String titre = formParams.getFirst("titre");
		if (titre == null || titre.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		}
		try {
			final int numero;
			if (formParams.get("numero") != null && formParams.getFirst("numero") != null
					&& !formParams.getFirst("numero").isBlank()) {
				numero = Integer.parseInt(formParams.getFirst("numero"));
			} else {
				numero = s.getEpisodes().size() + 1;
			}
			final Episode ep = new Episode(titre.trim(), numero);
			DaoBddHelper.getInstance().beginTransaction();
			DaoFactory.getInstance().getEpisodeDao().createEpisode(ep, false);
			s.addEpisode(ep);
			DaoFactory.getInstance().getSaisonDao().updateSaison(s, false);
			DaoBddHelper.getInstance().commitTransaction();
			final GenericEntity<Episode> json = new GenericEntity<>(ep) {
			};
			return Response.status(Response.Status.CREATED).entity(json).build();
		} catch (final NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("numero must be an integer").build();
		} catch (final Exception e) {
			try {
				DaoBddHelper.getInstance().rollbackTransaction();
			} catch (final DaoException ignored) {
				// ignore
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}
