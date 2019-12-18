package org.cofomo.authority.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Keystore {
	private static final String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIdYew1kre8ZUaZDeNt/ekxyIpW5A3RzdF9HxGAlSLK0rYKj+RMP/yjoH2cR6UlrIYThm6yoIXWtFcxWYwsBQ/8CAwEAAQ==";
	private static final String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAh1h7DWSt7xlRpkN42396THIilbkDdHN0X0fEYCVIsrStgqP5Ew//KOgfZxHpSWshhOGbrKghda0VzFZjCwFD/wIDAQABAkBL8E4ihqG8rKUON39BELZq3AMhpIwWs4zQxPCezWyLr1llAYwC/sboODvTwAW2O6Z1OWW3NuynuiomYxugSZDRAiEAuxzwllimsclYLsM/LGp9IkFbAJwvGLRqwSU7MGxAfWcCIQC5LI6VmIWRlIHiJShPwqziNImgra1smXj0mBZfewXNqQIgLS2/qY2eMi87a48ZIdBUhoDhSDRt6rklTZ1/vSFmMOECIFzle4qU/70vEZHQLk8FTRIWZU0UlEI9JC8g9WhlIV4xAiALHgYY76tTc8hPPCCu4l2ccrbPiRvGMNOXIQ4saaqjyQ==";

	public static PublicKey getPublicKey() {
		PublicKey returnKey = null;
		try {
			// convert string to rsa-public-key
			byte[] publicBytes = Base64.getMimeDecoder().decode(Keystore.publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			returnKey = keyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnKey;
	}

	public static PrivateKey getPrivateKey() {
		PrivateKey returnKey = null;
		try {
			// convert string to rsa-private-key
			byte[] privateBytes = Base64.getMimeDecoder().decode(Keystore.privateKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			returnKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnKey;
	}
}
