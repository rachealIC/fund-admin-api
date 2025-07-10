package com.racheal.fundadmin.fundadmin.dto;

import com.racheal.fundadmin.fundadmin.models.Fund;
import com.racheal.fundadmin.fundadmin.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FundResponseDto {
    private UUID fundId;
    private  String fundName;
    private String fundDescription;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TransactionResponseDto> transactions;


    public  FundResponseDto() {}
    public FundResponseDto(UUID fundId, String fundName, String fundDescription, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.fundId = fundId;
        this.fundName = fundName;
        this.fundDescription = fundDescription;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;


    }

    public UUID getId() {
        return fundId;
    }

    public void setId(UUID id) {
        this.fundId = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static FundResponseDto fromEntity(Fund fund) {
        return new FundResponseDto(
                fund.getFundId(),
                fund.getFundName(),
                fund.getFundDescription(),
                fund.getBalance(),
                fund.getCreatedAt(),
                fund.getUpdatedAt()


        );
    }



    @Override
    public String toString() {
        return "FundResponseDto{" +
                "fundId=" + fundId +
                ", fundName='" + fundName + '\'' +
                ", fundDescription='" + fundDescription + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
