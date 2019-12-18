package org.cofomo.authority.error;

public class JwtTokenExpiredError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JwtTokenExpiredError(String message) {
		super(message);
	}

}
