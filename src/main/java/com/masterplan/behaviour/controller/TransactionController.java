package com.masterplan.behaviour.controller;

import com.masterplan.behaviour.model.Financial;
import com.masterplan.behaviour.model.User;
import com.masterplan.behaviour.repository.FinancialRepository;
import com.masterplan.behaviour.repository.UserRepository;
import com.masterplan.behaviour.response.APIResponse;
import com.masterplan.behaviour.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private FinancialRepository financialRepository;

    @PostMapping("/record-transaction")
    public ResponseEntity<APIResponse> recordTransaction(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Financial transaction) {

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
        Financial financial = new Financial();
        financial.setDescription(transaction.getDescription());
        financial.setAmount(transaction.getAmount());
        financial.setTransactionDate(LocalDate.now());
        financial.setCategory(transaction.getCategory());
        financial.setType(transaction.getType());
        financial.setCreatedAt(LocalDateTime.now());
        financial.setUpdatedAt(LocalDateTime.now());
        financial.setUserId(user.getId());
        //financial.setId(user.getId()); // When Id is not auto generated then it will be null, so it will be inserted as new record

        // Save financial record
        financialRepository.save(financial);

        return ResponseEntity.ok(new APIResponse(200, "Transaction recorded successfully", financial));
    }
}
