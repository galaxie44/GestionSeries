package fr.pau.univ.series.services;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

/**
 * API REST saisons — POST /api/saison/add (token admin).
 */
@Path("/saison")
public class SaisonService {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSaison(final MultivaluedMap<String, String> formParams) {
		final Response forbidden = AuthenticationService.requireAdmin(formParams);
		if (forbidden != null) {
			return forbidden;
		}
		if (formParams.get("serieId") == null || formParams.get("nom") == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		}
		final int serieId;
		try {
			serieId = Integer.parseInt(formParams.getFirst("serieId"));
		} catch (final NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("serieId must be an integer").build();
		}
		final String nom = formParams.getFirst("nom");
		if (nom == null || nom.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		}
		try {
			final Serie serie = DaoFactory.getInstance().getSerieDao().readSerie(serieId);
			if (serie == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Parent serie not found").build();
			}
			final int numero;
			if (formParams.get("numero") != null && formParams.getFirst("numero") != null
					&& !formParams.getFirst("numero").isBlank()) {
				numero = Integer.parseInt(formParams.getFirst("numero"));
			} else {
				numero = serie.getSaisons().size() + 1;
			}
			final Saison saison = new Saison(nom.trim(), numero);
			DaoBddHelper.getInstance().beginTransaction();
			serie.addSaison(saison);
			DaoFactory.getInstance().getSerieDao().updateSerie(serie, false);
			DaoBddHelper.getInstance().commitTransaction();
			final GenericEntity<Saison> json = new GenericEntity<>(saison) {
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
