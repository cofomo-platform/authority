package org.cofomo.authority.error;

public class JwtTokenCreationError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JwtTokenCreationError() {
		super("Error while creating the JWT");
	}

}
