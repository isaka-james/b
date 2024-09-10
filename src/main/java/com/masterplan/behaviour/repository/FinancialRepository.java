package com.masterplan.behaviour.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masterplan.behaviour.model.Financial;

@Repository
public interface FinancialRepository extends CrudRepository<Financial, Integer> {

    // Custom query methods can be defined here

    // Example: Find financial records by category
    Optional<Financial> findByCategory(String category);

    // Example: Find financial records by type
    Optional<Financial> findByType(String type);

    // Example: Find financial records by transaction date
    Iterable<Financial> findByTransactionDate(LocalDate transactionDate);

}
