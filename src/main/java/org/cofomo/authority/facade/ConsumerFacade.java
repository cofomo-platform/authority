package org.cofomo.authority.facade;

import java.util.List;

import org.cofomo.authority.error.ConsumerNotFoundException;
import org.cofomo.authority.repository.ConsumerRepository;
import org.cofomo.commons.domain.identity.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerFacade {

	@Autowired
	ConsumerRepository consumerRepository;

	public Consumer getById(String id) {
		return consumerRepository.findById(id)
				.orElseThrow(() -> new ConsumerNotFoundException(id));
	}

	public List<Consumer> getAll() {
		return (List<Consumer>) consumerRepository.findAll();
	}

	public Consumer create(Consumer consumer) {
		return consumerRepository.save(consumer);
	}

	public void update(Consumer consumer, String id) {
		consumerRepository.save(consumer);
	}

	public void delete(String id) {
		consumerRepository.deleteById(id);
	}
	
}
