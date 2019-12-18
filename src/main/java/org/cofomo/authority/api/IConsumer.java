package org.cofomo.authority.api;

import java.util.List;

import org.cofomo.commons.domain.identity.Consumer;

public interface IConsumer {
	public List<Consumer> getAll();

	public Consumer getById(String id);

	public Consumer create(Consumer consumer);

	public void update(Consumer user, String id);

	public void delete(String id);
}
