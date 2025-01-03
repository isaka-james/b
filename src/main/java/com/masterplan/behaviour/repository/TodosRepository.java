package com.masterplan.behaviour.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.masterplan.behaviour.model.Todos;

public interface TodosRepository extends CrudRepository<Todos, Integer>{
    List<Todos> findByUserId(Integer userId);
}
