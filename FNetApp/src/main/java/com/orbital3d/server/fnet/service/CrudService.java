package com.orbital3d.server.fnet.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.server.fnet.service.item.ServiceItem;

public interface CrudService<S extends ServiceItem, T extends CrudRepository<S, Long>> {
	default T getRepository() {
		return null;
	}

	default Optional<S> findById(Long id) {
		return getRepository().findById(id);
	}

	@Transactional(TxType.REQUIRED)
	default S save(S item) {
		return getRepository().save(item);
	}

	@Transactional(TxType.REQUIRED)
	default S update(S item) {
		return getRepository().save(item);
	}

	@Transactional(TxType.REQUIRED)
	default void delete(S item) {
		getRepository().delete(item);
	}

	default Iterable<S> findAll() {
		return getRepository().findAll();
	}
}
