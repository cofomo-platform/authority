package org.cofomo.authority.utils;

import java.util.Date;

import org.cofomo.commons.domain.identity.VerifiableClaim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClaimIssuer  {
	
	@Value("${basepath}")
	private Object serverAddress;
	
	public VerifiableClaim generateClaim(String verfifiableClaimType, String id) {
		String validationEndpoint = serverAddress + "/v1/authorization/validateClaim";
		System.out.println(serverAddress);
		return new VerifiableClaim("https://www.w3.org/2018/credentials/v1", verfifiableClaimType, HashUtil.createHashOfString(id), "Authority of " + verfifiableClaimType, validationEndpoint, new Date());
	}
}
