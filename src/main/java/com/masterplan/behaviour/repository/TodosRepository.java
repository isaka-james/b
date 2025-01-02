package com.masterplan.behaviour.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.masterplan.behaviour.model.Todos;

public interface TodosRepository extends CrudRepository<Todos, Integer>{
    Optional<Todos> findByUserId(Integer userId);
}
