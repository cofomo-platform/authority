package org.cofomo.authority.error;

public class JwtTokenValidationError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JwtTokenValidationError() {
		super("JWT is not valid!");
	}

}
