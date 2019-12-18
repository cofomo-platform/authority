package org.cofomo.authority.facade;

import org.cofomo.authority.error.ConsumerNotFoundException;
import org.cofomo.authority.repository.ConsumerRepository;
import org.cofomo.authority.utils.JwtToken;
import org.cofomo.commons.domain.identity.Consumer;
import org.cofomo.commons.domain.identity.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
	
	@Autowired
	JwtToken jwtToken;
	
	@Autowired
    ConsumerRepository consumerRepository;

	public String authenticate(Credentials credentials) throws ConsumerNotFoundException{

		String username = credentials.getUsername();
		Consumer consumer = consumerRepository.findByUsername(username);
		if (consumer != null) {
			return jwtToken.generateToken(consumer);
		} else {
			throw new ConsumerNotFoundException(username);
		}   
	}
}
