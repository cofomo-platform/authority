package org.cofomo.authority.repository;

import org.cofomo.commons.domain.identity.Consumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, String> {
	public Consumer findByUsername(String username);
}
