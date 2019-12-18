package org.cofomo.authority.error;

public class ConsumerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConsumerNotFoundException(String username) {
		super("Consumer not found : " + username);
	}

}
