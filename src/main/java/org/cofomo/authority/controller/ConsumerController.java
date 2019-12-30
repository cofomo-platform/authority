package org.cofomo.authority.controller;

import java.util.List;

import org.cofomo.authority.api.IConsumer;
import org.cofomo.authority.facade.ConsumerFacade;
import org.cofomo.commons.domain.identity.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Consumer API", description = "Test class to CRUD consumer entities")
@RequestMapping(path = "/v1/consumer")
public class ConsumerController implements IConsumer {
	
	@Autowired
	ConsumerFacade facade;
	
	@Override
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create consumer")
	public Consumer create(@RequestBody Consumer consumer) {
		return facade.create(consumer);
	}
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get all consumers")
	public List<Consumer> getAll() {
		return facade.getAll();
	}

	@Override
	@GetMapping(path = "/{consumerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get mobility provider by id")
	public Consumer getById(@PathVariable String consumerId) {
		return facade.getById(consumerId);
	}

	
	@Override
	@PutMapping(path = "/{consumerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(summary = "Update consumer")
	public void update(@RequestBody Consumer consumer, @PathVariable String consumerId) {
		facade.update(consumer, consumerId);
	}

	@Override
	@DeleteMapping("/{consumerId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Delete consumer")
	public void delete(@PathVariable String consumerId) {
		facade.delete(consumerId);
	}

}
