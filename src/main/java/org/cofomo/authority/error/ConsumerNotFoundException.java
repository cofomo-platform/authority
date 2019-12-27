package org.cofomo.authority.error;

public class ConsumerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConsumerNotFoundException(String name) {
		super("Consumer not found : " + name);
	}

}
