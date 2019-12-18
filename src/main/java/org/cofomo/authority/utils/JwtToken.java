package org.cofomo.authority.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.cofomo.authority.error.JwtTokenExpiredError;
import org.cofomo.authority.error.JwtTokenValidationError;
import org.cofomo.authority.security.Keystore;
import org.cofomo.commons.domain.identity.Consumer;
import org.cofomo.commons.domain.identity.VerifiableClaim;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public Object getClaimFromToken(String token, String key) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.get(key);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(Keystore.getPublicKey()).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = new Date();
		try {
			expiration = getExpirationDateFromToken(token);
		} catch (Exception e) {
			throw new JwtTokenExpiredError(e.getMessage());
		}
		return expiration.before(new Date());
	}

	public String generateToken(Consumer consumer) {
		Map<String, Object> claims = new HashMap<>();
		consumer.setPassword("***");
		claims.put("consumer", consumer);
		return doGenerateToken(claims, consumer.getUsername());
	}
	
	public String generateTokenWithClaim(VerifiableClaim vc, String id) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(vc.getType(), vc);
		return doGenerateToken(claims, HashUtil.createHashOfString(id));
	}
	
	
	public Boolean validateToken(String token) {
		boolean isValid = !isTokenExpired(token);
		try {
			Jwts.parser().setSigningKey(Keystore.getPublicKey()).parseClaimsJws(token);
		} catch (JwtException e) {
			throw new JwtTokenValidationError();
		}
		return isValid;
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.RS256, Keystore.getPrivateKey()).compact();
	}

}
