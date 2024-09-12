package com.masterplan.behaviour.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    // Get the balance by user id (with custom query) where you get all,
    // transactions of the user with user_id of type='income' and sum the amount and minimize the amount sum of type='expense'
    // But expense is in positive and income is in positive also
    @Query("SELECT SUM(CASE WHEN type = 'income' THEN amount ELSE -amount END) FROM financials WHERE user_id = :userId")
    double getBalanceByUserId(@Param("userId") int userId);


}
