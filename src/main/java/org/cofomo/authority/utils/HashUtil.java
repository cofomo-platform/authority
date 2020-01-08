package org.cofomo.authority.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {
	public static String createHashOfString(String hashMe) {
		MessageDigest md;
		String hash = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(hashMe.getBytes());
			hash = Base64.getEncoder().encodeToString(digest);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error while hashing");
		}
		return hash;
	}
}
