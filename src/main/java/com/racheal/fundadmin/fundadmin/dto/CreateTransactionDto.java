package com.racheal.fundadmin.fundadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.racheal.fundadmin.fundadmin.models.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateTransactionDto {

    private UUID transactionId;

    @NotNull(message = "Fund ID is required")
    @JsonProperty("fund_id")
    private UUID fundId;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    @NotBlank(message = "Idempotency key is required")
    @JsonProperty("idempotency_key")
    private String idempotencyKey;



    private LocalDateTime createdAt;



    public CreateTransactionDto(TransactionType type, BigDecimal amount, UUID fundId, String idempotencyKey) {
        this.type = type;
        this.amount = amount;
        this.fundId = fundId;
        this.idempotencyKey = idempotencyKey;
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

    public UUID getFundId() {
        return fundId;
    }

    public void setFundId(UUID fundId) {
        this.fundId = fundId;
    }

    @Override
    public String toString() {
        return "CreateTransactionDto{" +
                "type=" + type +
                ", amount=" + amount +
                ", fundId=" + fundId +
                '}';
    }
}
