package com.racheal.fundadmin.fundadmin.repository;

import com.racheal.fundadmin.fundadmin.dto.TransactionResponseDto;
import com.racheal.fundadmin.fundadmin.models.Transaction;
import com.racheal.fundadmin.fundadmin.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

//    @Query("""
//    SELECT t FROM Transaction t
//    WHERE (:fundId IS NULL OR t.fund.fundId = :fundId)
//      AND (:type IS NULL OR t.type = :type)
//      AND (:from IS NULL OR t.transactionDate >= :from)
//      AND (:to IS NULL OR t.transactionDate <= :to)
//""")
//    List<Transaction> findFilteredTransactions(
//            @Param("fundId") UUID fundId,
//            @Param("type") TransactionType type,
//            @Param("from") LocalDateTime from,
//            @Param("to") LocalDateTime to
////            Pageable pageable
//    );

    @Query("""
    SELECT t FROM Transaction t
    WHERE (:fundId IS NULL OR t.fund.fundId = :fundId)
      AND (:type IS NULL OR t.type = :type)
      AND (:fromDate IS NULL OR t.transactionDate >= :fromDate)
      AND (:toDate IS NULL OR t.transactionDate <= :toDate)
""")
    List<Transaction> findFilteredTransactions(
            @Param("fundId") UUID fundId,
            @Param("type") TransactionType type,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );



//    UUID fundId = UUID.fromString("129cf53b-ae61-4210-9833-18c3ead5cd36");


    @Query("SELECT t FROM Transaction t WHERE t.fund.fundId = :fundId")
    List<Transaction> findAllByFundId(@Param("fundId") UUID fundId);




//    @Query("SELECT t FROM Transaction t WHERE t.idempotencyKey = :idempotencyKey ")
//    Optional<Transaction> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);
}


//LOG TO SOMEWHERE
//ERROR MESSAGES, CUSTOM
//API ROUTES
//RESPONSE STRUCTURE
//IDEMPOTENCY