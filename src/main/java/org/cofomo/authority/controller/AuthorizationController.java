package org.cofomo.authority.controller;

import org.cofomo.authority.api.IAuthorization;
import org.cofomo.authority.facade.AuthorizationFacade;
import org.cofomo.authority.utils.JwtDTO;
import org.cofomo.authority.utils.RequestClaimDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Authorization API", description = "Implements IAuthorization")
@RequestMapping(path = "/v1/authorization")
public class AuthorizationController implements IAuthorization {
	
	@Autowired
	AuthorizationFacade facade;
	
	@Override
	@PostMapping(path="/requestClaim", consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Request claim of authority")
	public JwtDTO requestClaim(@RequestBody RequestClaimDTO dto) {
		return facade.requestClaimToken(dto.getJwt(), dto.getVct());
	}
	
	@Override
	@PostMapping(path="/validateClaim", consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Validate claim")
	public boolean validateClaim(@RequestBody JwtDTO dto) {
		return facade.validateClaim(dto.getJwt());
	}

}

