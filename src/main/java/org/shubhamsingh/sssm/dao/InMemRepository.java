package org.shubhamsingh.sssm.dao;

import java.io.Serializable;
import java.util.Optional;

public interface InMemRepository<T, ID extends Serializable> {

    Iterable<T> findAll();

    Iterable<T> findAll(Iterable<ID> ids);

    Optional<T> findById(ID id);

    T save(T entity);

    Iterable<T> save(Iterable<T> entities);

    void delete(T entity);

}
