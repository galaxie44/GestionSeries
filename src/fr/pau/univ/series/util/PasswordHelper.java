package fr.pau.univ.series.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Hash / vérification de mots de passe (annexe PDF Rest API p.28).
 */
public final class PasswordHelper {

	private static final String SALT = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;

	private PasswordHelper() {
	}

	public static String generateSecurePassword(final String password) {
		final byte[] securePassword = hash(password.toCharArray(), SALT.getBytes());
		return Base64.getEncoder().encodeToString(securePassword);
	}

	public static boolean verifyUserPassword(final String providedPassword, final String securedPassword) {
		final String newSecurePassword = generateSecurePassword(providedPassword);
		return newSecurePassword.equals(securedPassword);
	}

	private static byte[] hash(final char[] password, final byte[] salt) {
		final PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}
}
