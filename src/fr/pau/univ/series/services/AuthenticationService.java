package fr.pau.univ.series.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.exception.ServiceException;
import fr.pau.univ.series.model.User;
import fr.pau.univ.series.model.UserRole;
import fr.pau.univ.series.util.PasswordHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

/**
 * Authentification JWT (PDF Rest API p.21–27).
 * <p>
 * POST /api/auth/login — public<br>
 * checkToken / createJWT — utilitaires pour les autres services
 */
@Path("/auth")
public class AuthenticationService {

	/** Durée de vie du token : 2 h. */
	public static final long TOKEN_LIFETIME_MS = 2L * 60L * 60L * 1000L;

	/**
	 * Clé secrète (≥ 256 bits pour HS256). En prod : hors du code / variables d'env.
	 */
	private static final String SECRET_API_KEY = "GestionSeriesLocalSecretKeyForJwtHS256!!";

	private static Key signingKey() {
		final byte[] keyBytes = SECRET_API_KEY.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response autenticateUser(final MultivaluedMap<String, String> formParams) {
		if (formParams.get("user") == null || formParams.get("pwd") == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		}
		final String login = formParams.getFirst("user");
		final String pwd = formParams.getFirst("pwd");
		if (login == null || login.isBlank() || pwd == null || pwd.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid parameters").build();
		}
		try {
			final User usr = DaoFactory.getInstance().getUserDao().readUserByLogin(login.trim());
			if (usr == null || !PasswordHelper.verifyUserPassword(pwd, usr.getPassword())) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Unknown user or bad password").build();
			}
			final String token = createJWT(usr, TOKEN_LIFETIME_MS);
			return Response.ok(token).build();
		} catch (final DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	public static String createJWT(final User user, final long ttlMillis) {
		final long nowMillis = System.currentTimeMillis();
		final Date now = new Date(nowMillis);
		final String id = UUID.randomUUID().toString();
		return Jwts.builder()
				.setId(id)
				.setIssuedAt(now)
				.setSubject(user.getRole().name())
				.setIssuer(user.getLogin())
				.signWith(signingKey(), SignatureAlgorithm.HS256)
				.setExpiration(new Date(nowMillis + ttlMillis))
				.compact();
	}

	public static User checkToken(final MultivaluedMap<String, String> formParams) throws ServiceException {
		if (formParams.get("token") == null) {
			throw new ServiceException("Vous devez fournir un token d'identification valide");
		}
		final String token = formParams.getFirst("token");
		if (token == null || token.isBlank()) {
			throw new ServiceException("Vous devez fournir un token d'identification valide");
		}
		final Claims claims;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(signingKey())
					.build()
					.parseClaimsJws(token.trim())
					.getBody();
		} catch (final Exception e) {
			throw new ServiceException("Token invalide");
		}
		final Date now = new Date(System.currentTimeMillis());
		if (claims.getExpiration() != null && now.after(claims.getExpiration())) {
			throw new ServiceException("La durée de validité du Token est expirée.");
		}
		final User ret = new User();
		ret.setLogin(claims.getIssuer());
		ret.setRole(UserRole.valueOf(claims.getSubject()));
		return ret;
	}

	/** Vérifie le token et le rôle ADMIN ; renvoie une Response d'erreur ou {@code null} si OK. */
	public static Response requireAdmin(final MultivaluedMap<String, String> formParams) {
		try {
			final User usr = checkToken(formParams);
			if (!UserRole.ROLE_ADMIN.equals(usr.getRole())) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Only admin can do this").build();
			}
			return null;
		} catch (final ServiceException e) {
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}
}
