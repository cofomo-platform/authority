package org.cofomo.authority.api;

import org.cofomo.authority.utils.JwtDTO;
import org.cofomo.authority.utils.RequestClaimDTO;

public interface IAuthorization {
	public JwtDTO requestClaim(RequestClaimDTO dto);
	public boolean validateClaim(JwtDTO dto);
}
