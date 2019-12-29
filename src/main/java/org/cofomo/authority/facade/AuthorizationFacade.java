package org.cofomo.authority.facade;

import java.util.HashMap;

import org.cofomo.authority.utils.ClaimIssuer;
import org.cofomo.authority.utils.JwtDTO;
import org.cofomo.authority.utils.JwtToken;
import org.cofomo.commons.domain.identity.VerifiableClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFacade {
	
	@Autowired
	JwtToken jwtToken;
	
	@Autowired
	ClaimIssuer claimIssuer;
	
	public boolean validateClaim(String jwt) {
		return jwtToken.validateToken(jwt);
	}

	public JwtDTO requestClaimToken(String authToken, String vct) {
		String token = null;
		if (jwtToken.validateToken(authToken)) {
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) jwtToken.getClaimFromToken(authToken, "consumer");
			VerifiableClaim vc = claimIssuer.generateClaim(vct, map.get("id"));
			token = jwtToken.generateTokenWithClaim(vc, map.get("id"));
		} 
		return new JwtDTO(token);
	}
}
