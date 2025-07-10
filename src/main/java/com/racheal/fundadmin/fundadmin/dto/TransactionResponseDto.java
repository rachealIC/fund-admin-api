package com.racheal.fundadmin.fundadmin.dto;

import com.racheal.fundadmin.fundadmin.models.Fund;
import com.racheal.fundadmin.fundadmin.models.Transaction;
import com.racheal.fundadmin.fundadmin.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionResponseDto {

    private UUID transactionId;
    private UUID fundId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public TransactionResponseDto() {}

    public TransactionResponseDto(UUID transactionId, UUID fundId, TransactionType type, BigDecimal amount, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.fundId = fundId;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
    }




    public static TransactionResponseDto fromEntity(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getTransactionId(),
                transaction.getFund().getFundId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTransactionDate()

        );
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getFundId() {
        return fundId;
    }

    public void setFundId(UUID fundId) {
        this.fundId = fundId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
