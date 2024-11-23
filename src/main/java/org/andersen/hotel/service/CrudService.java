package org.andersen.hotel.service;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    T save(T entity);

    Optional<T> getById(Long id);

    List<T> getAll();

    void delete(Long id);

    List<T> getAllSorted(Sort sort);
}
