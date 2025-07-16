package com.racheal.fundadmin.fundadmin.web;

import com.racheal.fundadmin.fundadmin.dto.CreateTransactionDto;
import com.racheal.fundadmin.fundadmin.dto.FundSummaryDto;
import com.racheal.fundadmin.fundadmin.dto.TransactionResponseDto;
import com.racheal.fundadmin.fundadmin.models.Transaction;
import com.racheal.fundadmin.fundadmin.models.TransactionType;
import com.racheal.fundadmin.fundadmin.resources.ApiResponse;
import com.racheal.fundadmin.fundadmin.resources.exceptions.DuplicateResourceException;
import com.racheal.fundadmin.fundadmin.service.IdempotencyService;
import com.racheal.fundadmin.fundadmin.service.TransactionService;
import com.racheal.fundadmin.fundadmin.utilities.HashUtil;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;
    private final IdempotencyService idempotencyService;

    public TransactionController(TransactionService transactionService, IdempotencyService idempotencyService) {
        this.transactionService = transactionService;
        this.idempotencyService = idempotencyService;
    }


    @PostMapping("/transactions")
    public ResponseEntity<ApiResponse<UUID>> createTransaction(
            @RequestHeader(value = "Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid CreateTransactionDto createTransactionDto) {

        String requestHash = HashUtil.hash(createTransactionDto);

        if (idempotencyService.exists(idempotencyKey)) {
            String existingHash = idempotencyService.get(idempotencyKey);

            if (!existingHash.equals(requestHash)) {
                throw new DuplicateRequestException("Idempotency key conflict: request data does not match.");

            }

            throw new DuplicateResourceException("Idempotency  conflict:");

//            UUID existingResponse = idempotencyService.getResponse(idempotencyKey);
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>( );

        }

        idempotencyService.save(idempotencyKey, requestHash);

        TransactionResponseDto transaction = transactionService.saveTransaction(createTransactionDto);
        ApiResponse<UUID> response = new ApiResponse<>("Transaction created", transaction.getTransactionId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);


    }

//    @GetMapping("/transactions/deposits/{fundId}")
//    public ResponseEntity<BigDecimal> totalDeposits(@PathVariable UUID fundId) {
//        BigDecimal totalDeposit = transactionService.getTotalDeposits(fundId);
//        return new ResponseEntity<>(totalDeposit, HttpStatus.OK);
//    }


    @GetMapping("/funds/{fundId}/summary")
    public ResponseEntity<FundSummaryDto> getTransactionSummary(@PathVariable UUID fundId) {
        FundSummaryDto fundSummary = transactionService.getFundSummary(fundId);
        return new ResponseEntity<>(fundSummary, HttpStatus.OK);
    }

//    @GetMapping("/transactions")
//    public ResponseEntity<ApiResponse<List<TransactionResponseDto>>> getTransactions(
//            @RequestParam(required = false) UUID fundId,
//            @RequestParam(required = false) TransactionType type,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
////            Pageable pageable
//    ) {
//
//        List<Transaction> transactions = transactionService.getFilteredTransactions(fundId, type, from, to);
//        List<TransactionResponseDto> responseDtos = transactions.stream().map(TransactionResponseDto::fromEntity).toList();
//        return ResponseEntity.ok(new ApiResponse<>("Transactions", responseDtos));
//
//    }


@GetMapping("/funds/{fundId}/transactions")
public ResponseEntity<ApiResponse<List<TransactionResponseDto>>> getTransactionsByFundId(
        @PathVariable("fundId") UUID fundId
) {
    List<TransactionResponseDto> transactions = transactionService.getAllTransactions(fundId);
    return ResponseEntity.ok(new ApiResponse<>("Transactions retrieved successfully", transactions));
}


}
