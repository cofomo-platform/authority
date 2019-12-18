package org.cofomo.authority.controller;

import org.cofomo.authority.api.IAuthentication;
import org.cofomo.authority.facade.AuthenticationFacade;
import org.cofomo.commons.domain.identity.Credentials;
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
@Tag(name = "Authentication API", description = "Implements IAuthentication")
@RequestMapping(path = "/v1/authenticate")
public class AuthenticationController implements IAuthentication {
	
	@Autowired
	AuthenticationFacade facade;
	
	@Override
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Authenticate consumer")
	public String authenticate(@RequestBody Credentials credentials) {
		return facade.authenticate(credentials);
	}

}
