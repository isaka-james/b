package com.masterplan.behaviour.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.masterplan.behaviour.model.Todos;
import com.masterplan.behaviour.model.User;
import com.masterplan.behaviour.repository.TodosRepository;
import com.masterplan.behaviour.repository.UserRepository;
import com.masterplan.behaviour.response.APIResponse;
import com.masterplan.behaviour.service.TokenService;

@RestController
public class TodosController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TodosRepository todosRepository;

    @PostMapping("/record-todos")
    public ResponseEntity<APIResponse> recordTodos(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Todos todos) {

        // Extract token and username
        String token = authHeader.replace("Bearer ", "");
        String username = tokenService.getUsernameFromToken(token);

        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new APIResponse(400, "User not found", null));
        }
        User user = userOptional.get();

        // Create a Financial record
        Todos todosToSave = new Todos();
        todosToSave.setDescription(todos.getDescription());
        todosToSave.setTitle(todos.getTitle());
        todosToSave.setStatus(todos.getStatus());
	todosToSave.setCategory(todos.getCategory());
	todosToSave.setStared(todos.getStared());
        todosToSave.setCompletedDate(todos.getCompletedDate());
        todosToSave.setRating(todos.getRating());
        todosToSave.setCreatedAt(LocalDateTime.now());
        todosToSave.setUpdatedAt(LocalDateTime.now());
	todosToSave.setTargetAt(todos.getTargetAt());
        todosToSave.setUserId(user.getId());
        //financial.setId(user.getId()); // When Id is not auto generated then it will be null, so it will be inserted as new record

        // Save financial record
        todosRepository.save(todosToSave);

        return ResponseEntity.ok(new APIResponse(200, "Todos recorded successfully", todosToSave));
    }

    @GetMapping("/get-todos")
    public ResponseEntity<APIResponse> getTodos(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = tokenService.getUsernameFromToken(token);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new APIResponse(400, "User not found", null));
        }
        User user = userOptional.get();

        List<Todos> todos = todosRepository.findByUserId(user.getId());

        if (todos.isEmpty()) {
            return ResponseEntity.ok(new APIResponse(200, "No todos found for the user", Collections.emptyList()));
        }

        return ResponseEntity.ok(new APIResponse(200, "Todos fetched successfully", todos));
    }
}
