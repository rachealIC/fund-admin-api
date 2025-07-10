package com.racheal.fundadmin.fundadmin.repository;

import com.racheal.fundadmin.fundadmin.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    //COALESCE IS USE TO PREVENT NULL EXCEPTIONS, T AS AN ALIAS OF THE ENTITY
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.fund.fundId = :fundId and t.type = 'DEPOSIT' ")
    BigDecimal sumDeposits(@Param("fundId") UUID fundId);


    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.fund.fundId = :fundId and t.type = 'WITHDRAWAL' ")
    BigDecimal sumWithdrawals(@Param("fundId") UUID fundId);


    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.fund.fundId = :fundId and t.type = 'INVESTMENT_GAIN' ")
    BigDecimal sumGains(@Param("fundId") UUID fundId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.fund.fundId = :fundId and t.type = 'INVESTMENT_LOSS' ")
    BigDecimal sumLosses(@Param("fundId") UUID fundId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.fund.fundId = :fundId and t.type = 'FEE' ")
    BigDecimal sumFees(@Param("fundId") UUID fundId);

    @Query("SELECT t FROM Transaction t WHERE t.idempotencyKey = :idempotencyKey ")
    Optional<Transaction> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);
}


//LOG TO SOMEWHERE
//ERROR MESSAGES, CUSTOM
//API ROUTES
//RESPONSE STRUCTURE
//IDEMPOTENCY