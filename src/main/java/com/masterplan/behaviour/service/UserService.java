package com.masterplan.behaviour.service;

import com.masterplan.behaviour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Integer> findUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> user.getId());
    }
}
