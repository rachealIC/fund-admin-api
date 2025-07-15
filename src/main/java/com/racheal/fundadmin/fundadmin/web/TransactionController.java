package com.racheal.fundadmin.fundadmin.web;

import com.racheal.fundadmin.fundadmin.dto.CreateTransactionDto;
import com.racheal.fundadmin.fundadmin.dto.FundSummaryDto;
import com.racheal.fundadmin.fundadmin.dto.TransactionResponseDto;
import com.racheal.fundadmin.fundadmin.models.Transaction;
import com.racheal.fundadmin.fundadmin.resources.ApiResponse;
import com.racheal.fundadmin.fundadmin.resources.exceptions.DuplicateResourceException;
import com.racheal.fundadmin.fundadmin.service.IdempotencyService;
import com.racheal.fundadmin.fundadmin.service.TransactionService;
import com.racheal.fundadmin.fundadmin.utilities.HashUtil;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
            @RequestHeader(value= "Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid CreateTransactionDto createTransactionDto) {

        String requestHash = HashUtil.hash(createTransactionDto);

        if(idempotencyService.exists(idempotencyKey)) {
            String existingHash = idempotencyService.get(idempotencyKey);

            if(!existingHash.equals(requestHash)) {
                throw new DuplicateRequestException("Idempotency key conflict: request data does not match.");

            }

            throw new DuplicateResourceException("Idempotency  conflict:");

//            UUID existingResponse = idempotencyService.getResponse(idempotencyKey);
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>( );

        }

        idempotencyService.save(idempotencyKey, requestHash);

        TransactionResponseDto transaction =  transactionService.saveTransaction(createTransactionDto);
        ApiResponse<UUID> response = new ApiResponse<>("Transaction created", transaction.getTransactionId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);


    }

//    @GetMapping("/transactions/deposits/{fundId}")
//    public ResponseEntity<BigDecimal> totalDeposits(@PathVariable UUID fundId) {
//        BigDecimal totalDeposit = transactionService.getTotalDeposits(fundId);
//        return new ResponseEntity<>(totalDeposit, HttpStatus.OK);
//    }


    @GetMapping("/funds/{fundId}/summary")
    public ResponseEntity<FundSummaryDto>  getTransactionSummary(@PathVariable UUID fundId) {
        FundSummaryDto fundSummary = transactionService.getFundSummary(fundId);
        return  new ResponseEntity<>(fundSummary, HttpStatus.OK);
    }
}
