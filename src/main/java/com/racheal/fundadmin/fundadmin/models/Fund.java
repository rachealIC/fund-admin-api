package com.racheal.fundadmin.fundadmin.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "funds")
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "fund_id", updatable = false, nullable = false)
    @JsonProperty("fund_id")
    private UUID fundId;

    @Column(name = "fund_name", nullable = false, unique = true, length = 100)
    @JsonProperty("fund_name")
    private String fundName;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Column(name = "fund_description", length = 500)
    @JsonProperty("fund_description")
    private String fundDescription;

    // Use BigDecimal for money - never use Double for financial data
    @Column(name = "balance", precision = 19, scale = 2, nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "fund", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    // Default constructor (required by JPA)
    public Fund() {}

    // Constructor
    public Fund(String fundName, String fundDescription, BigDecimal balance) {
        this.fundName = fundName;
        this.fundDescription = fundDescription;
        this.balance = balance;
    }

    // Getters and Setters
    public UUID getFundId() {
        return fundId;
    }

    public void setFundId(UUID fundId) {
        this.fundId = fundId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundDescription() {
        return fundDescription;
    }

    public void setFundDescription(String fundDescription) {
        this.fundDescription = fundDescription;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "fundId=" + fundId +
                ", fundName='" + fundName + '\'' +
                ", fundDescription='" + fundDescription + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}