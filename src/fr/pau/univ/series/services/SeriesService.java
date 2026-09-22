package fr.pau.univ.series.services;

import java.util.List;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

/**
 * API REST séries — Phase B (+ mutations protégées Phase C).
 * <ul>
 * <li>GET /api/serie/all</li>
 * <li>GET /api/serie/{id}</li>
 * <li>POST /api/serie/add (token admin)</li>
 * <li>DELETE /api/serie/delete/{id}?token=… (token admin)</li>
 * </ul>
 */
@Path("/serie")
public class SeriesService {

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSeries() {
		try {
			final List<Serie> ret = DaoFactory.getInstance().getSerieDao().readAllSeries();
			final GenericEntity<List<Serie>> json = new GenericEntity<>(ret) {
			};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSerieById(@PathParam("id") final int idSerie) {
		try {
			final Serie s = DaoFactory.getInstance().getSerieDao().readSerie(idSerie);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Serie not found").build();
			}
			final GenericEntity<Serie> json = new GenericEntity<>(s) {
			};
			return Response.ok().entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSerie(final MultivaluedMap<String, String> formParams) {
		final Response forbidden = AuthenticationService.requireAdmin(formParams);
		if (forbidden != null) {
			return forbidden;
		}
		if (formParams.get("nom") == null || formParams.getFirst("nom") == null
				|| formParams.getFirst("nom").isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		}
		try {
			final Serie created = DaoFactory.getInstance().getSerieDao()
					.createSerie(new Serie(formParams.getFirst("nom").trim()), true);
			final GenericEntity<Serie> json = new GenericEntity<>(created) {
			};
			return Response.status(Response.Status.CREATED).entity(json).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSerie(@PathParam("id") final int idSerie,
			@jakarta.ws.rs.QueryParam("token") final String token) {
		final MultivaluedMap<String, String> params = new jakarta.ws.rs.core.MultivaluedHashMap<>();
		if (token != null) {
			params.add("token", token);
		}
		final Response forbidden = AuthenticationService.requireAdmin(params);
		if (forbidden != null) {
			return forbidden;
		}
		try {
			final Serie s = DaoFactory.getInstance().getSerieDao().readSerie(idSerie);
			if (s == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Serie not found").build();
			}
			DaoFactory.getInstance().getSerieDao().deleteSerie(s, true);
			return Response.ok().entity("Serie deleted").build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}
