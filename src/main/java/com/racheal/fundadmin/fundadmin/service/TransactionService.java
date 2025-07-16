package com.racheal.fundadmin.fundadmin.service;

import com.racheal.fundadmin.fundadmin.dto.CreateTransactionDto;
import com.racheal.fundadmin.fundadmin.dto.FundSummaryDto;
import com.racheal.fundadmin.fundadmin.dto.TransactionResponseDto;
import com.racheal.fundadmin.fundadmin.models.Fund;
import com.racheal.fundadmin.fundadmin.models.Transaction;
import com.racheal.fundadmin.fundadmin.models.TransactionType;
import com.racheal.fundadmin.fundadmin.repository.FundRepository;
import com.racheal.fundadmin.fundadmin.repository.TransactionRepository;
import com.racheal.fundadmin.fundadmin.resources.exceptions.InsufficientBalanceException;
import com.racheal.fundadmin.fundadmin.resources.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FundRepository fundRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public TransactionService(TransactionRepository transactionRepository, FundRepository fundRepository) {
        this.transactionRepository = transactionRepository;
        this.fundRepository = fundRepository;
    }

    public TransactionResponseDto saveTransaction(CreateTransactionDto createTransactionDto) {
        logger.info("Saving transaction: {}", createTransactionDto);

//        Optional<Transaction> idempotencyKey = transactionRepository.findByIdempotencyKey(createTransactionDto.getIdempotencyKey());
//        if (idempotencyKey.isPresent()) {
//            logger.info("Duplicate transaction detected. Returning existing transaction.");
//            return TransactionResponseDto.fromEntity(idempotencyKey.get());
//        }

        Fund fund = fundRepository.findById(createTransactionDto.getFundId()).orElseThrow(() -> new ResourceNotFoundException("Fund not found"));
        logger.info("Found fund: {}", fund.getFundName());




        BigDecimal fundBalance = fund.getBalance();
        BigDecimal amount = createTransactionDto.getAmount();

        switch (createTransactionDto.getType()) {
            case DEPOSIT, INVESTMENT_GAIN -> fund.setBalance((fundBalance.add(amount)));
            case WITHDRAWAL, FEE, INVESTMENT_LOSS -> {
                if (fundBalance.compareTo(amount) < 0) {
                    logger.error("Insufficient balance for transaction type {}", createTransactionDto.getType());
                    throw new InsufficientBalanceException("Not enough balance for this transaction.");
                }
                fund.setBalance(fundBalance.subtract(amount));
            }
        }

        Transaction transaction = new Transaction(
                fund,
                createTransactionDto.getType(),
                createTransactionDto.getAmount()

        );

           Transaction savedTransaction = transactionRepository.save(transaction);
           logger.info("Saving transaction: {}", transaction.getTransactionId());
           return TransactionResponseDto.fromEntity(savedTransaction);



    }


    public  BigDecimal getTotalDeposits(UUID fundId){
        return  transactionRepository.sumDeposits(fundId);
    }

    public  BigDecimal getTotalWithdrawals(UUID fundId){
        return transactionRepository.sumWithdrawals(fundId);
    }


    public FundSummaryDto getFundSummary(UUID fundId){
        logger.info("Getting fund summary: {}", fundId);

        Fund fund = fundRepository.findById(fundId).orElseThrow(() -> new ResourceNotFoundException("Fund not found"));

        BigDecimal totalDeposits = getTotalDeposits(fundId);
        BigDecimal totalWithdrawals = getTotalWithdrawals(fundId);
        BigDecimal gains = transactionRepository.sumGains(fundId);
        BigDecimal losses = transactionRepository.sumLosses(fundId);
        BigDecimal fees = transactionRepository.sumFees(fundId);

        logger.info("Total deposits: {}", totalDeposits);
        logger.info("Total withdrawals: {}", totalWithdrawals);
        logger.info("Total gains: {}", gains);

        BigDecimal netProfit = totalDeposits.add(gains).subtract(totalWithdrawals).subtract(losses).subtract(fees);
        logger.info("Net profit: {}", netProfit);

        return new FundSummaryDto(
                fund.getFundId(),
                fund.getFundName(),
                fund.getBalance(),
                totalDeposits,
                totalWithdrawals,
                gains,
                losses,
                netProfit



        );
    }


    public List<Transaction> getFilteredTransactions(UUID fundId, TransactionType type, LocalDateTime from, LocalDateTime to) {
        return  transactionRepository.findFilteredTransactions(
                fundId,
                type,
                from != null ? from : LocalDateTime.of(1900, 1, 1, 0, 0),
                to != null ? to : LocalDateTime.of(2999, 12, 31, 23, 59));

    }


    public List<TransactionResponseDto> getAllTransactions(UUID fundId) {
        List<Transaction> transactions =  transactionRepository.findAllByFundId(fundId);
        return  transactions.stream().map(TransactionResponseDto::fromEntity).toList();
    }
}
