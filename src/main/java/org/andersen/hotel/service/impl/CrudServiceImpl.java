package org.andersen.hotel.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.andersen.hotel.service.CrudService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class CrudServiceImpl<T> implements CrudService<T> {

    protected final JpaRepository<T, Long> repository;

    public CrudServiceImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<T> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .ifPresentOrElse(hotel -> repository.deleteById(id), () -> {
                    throw new EntityNotFoundException();
                });
    }

    @Override
    public List<T> getAllSorted(Sort sort) {
        return repository.findAll(sort);
    }
}
