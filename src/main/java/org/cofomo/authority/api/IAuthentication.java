package org.cofomo.authority.api;

import java.util.List;

import org.cofomo.commons.domain.identity.Consumer;
import org.cofomo.commons.domain.identity.Credentials;

public interface IAuthentication {
	
	public String authenticate(Credentials credentials);
}
