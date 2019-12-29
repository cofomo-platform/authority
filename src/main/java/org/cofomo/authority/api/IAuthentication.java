package org.cofomo.authority.api;

import org.cofomo.authority.utils.JwtDTO;
import org.cofomo.commons.domain.identity.Credentials;

public interface IAuthentication {
	
	public JwtDTO authenticate(Credentials credentials);
}
