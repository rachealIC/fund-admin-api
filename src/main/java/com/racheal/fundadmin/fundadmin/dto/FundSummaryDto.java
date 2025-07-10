package com.racheal.fundadmin.fundadmin.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class FundSummaryDto {
    private UUID fundId;
    private String fundName;
    private BigDecimal currentBalance;
    private BigDecimal totalDeposit;
    private BigDecimal totalWithdrawal;
    private  BigDecimal totalGain;
    private  BigDecimal totalLoss;

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

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public BigDecimal getTotalWithdrawal() {
        return totalWithdrawal;
    }

    public void setTotalWithdrawal(BigDecimal totalWithdrawal) {
        this.totalWithdrawal = totalWithdrawal;
    }

    public BigDecimal getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(BigDecimal totalGain) {
        this.totalGain = totalGain;
    }

    public BigDecimal getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(BigDecimal totalLoss) {
        this.totalLoss = totalLoss;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public FundSummaryDto(UUID fundId, String fundName, BigDecimal currentBalance, BigDecimal totalDeposit,
                          BigDecimal totalWithdrawal, BigDecimal totalGain, BigDecimal totalLoss, BigDecimal netProfit) {
        this.fundId = fundId;
        this.fundName = fundName;
        this.currentBalance = currentBalance;
        this.totalDeposit = totalDeposit;
        this.totalWithdrawal = totalWithdrawal;
        this.totalGain = totalGain;
        this.totalLoss = totalLoss;
        this.netProfit = netProfit;
    }

    private BigDecimal netProfit;

    public FundSummaryDto() {

    }

}


