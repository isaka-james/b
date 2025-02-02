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
    
        System.out.println("Authorization Token: " + token);
        System.out.println("Extracted Username: " + username);
        
        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            System.out.println("User not found for username: " + username);
            return ResponseEntity.badRequest().body(new APIResponse(400, "User not found", null));
        }
        User user = userOptional.get();
    
        // Create a Financial record
        Financial financial = new Financial();
        financial.setDescription(transaction.getDescription());
        financial.setAmount(transaction.getAmount());
        financial.setTransactionDate(transaction.getTransactionDate());
        financial.setCategory(transaction.getCategory());
        financial.setType(transaction.getType());
        financial.setCreatedAt(LocalDateTime.now());
        financial.setUpdatedAt(LocalDateTime.now());
        financial.setUserId(user.getId());
    
        // Save financial record
        Financial savedFinancial = financialRepository.save(financial);
    
        System.out.println("Transaction saved successfully with ID: " + savedFinancial.getId());
    
        return ResponseEntity.ok(new APIResponse(200, "Transaction recorded successfully", savedFinancial));
    }
    
    @GetMapping("/get-balance")
    public ResponseEntity<APIResponse> getBalance(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = tokenService.getUsernameFromToken(token);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new APIResponse(400, "User not found", null));
        }
        User user = userOptional.get();

        // Calculate balance
        double balance = financialRepository.getBalanceByUserId(user.getId());

        return ResponseEntity.ok(new APIResponse(200, "Balance fetched successfully", balance));
    }
}
