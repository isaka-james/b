package com.masterplan.behaviour.repository;


import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import com.masterplan.behaviour.model.User;


public interface UserRepository extends ListCrudRepository<User,Integer> {
    Optional<User> findByUsername(String username);

}

