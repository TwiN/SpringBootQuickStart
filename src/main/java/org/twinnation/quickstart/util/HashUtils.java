package org.twinnation.quickstart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;


public class HashUtils {
	
	private static Logger logger = LoggerFactory.getLogger(HashUtils.class);
	
	
	/**
	 * Prevents instantiation
	 */
	private HashUtils() {}
	
	
	public static String sha256WithRandomSalt(String str) {
		String salt = getSalt();
		return sha256(str + salt) + ":" + salt;
	}
	
	
	public static boolean validateSha256WithSalt(String str, String hashSalt) {
		String hash = hashSalt.split(":")[0];
		String salt = hashSalt.split(":")[1];
		return sha256(str + salt).equalsIgnoreCase(hash);
	}
	
	
	public static String sha256(String str) {
		String result = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(str.getBytes("UTF-8"));
			result = new BigInteger(1, bytes).toString(16);
		} catch (Exception e) {
			logger.error("[sha256] Failed to hash String '" + str + "': " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static String getSalt() {
		byte[] salt = new byte[24];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
}
