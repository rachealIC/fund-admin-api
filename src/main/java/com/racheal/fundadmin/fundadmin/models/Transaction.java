package com.racheal.fundadmin.fundadmin.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private UUID transactionId;

    //It fetches the related table when needed with the Lazy
    //every fund can have multiple transactions
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id", nullable = false)
    @JsonProperty("fund_id")
    private Fund fund;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "transaction_date", updatable = false)
    private LocalDateTime transactionDate;



    public Transaction() {

    }

//    public Transaction(UUID idempotencyKey) {
//        this.idempotencyKey = idempotencyKey;
//    }



    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
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

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

//    public String getIdempotencyKey() {
//        return idempotencyKey;
//    }
//
//    public void setIdempotencyKey(String idempotencyKey) {
//        this.idempotencyKey = idempotencyKey;
//    }

    public Transaction(Fund fund, TransactionType type, BigDecimal amount) {
        this.fund = fund;
        this.type = type;
        this.amount = amount;
//        this.idempotencyKey = idempotencyKey;
    }


}
