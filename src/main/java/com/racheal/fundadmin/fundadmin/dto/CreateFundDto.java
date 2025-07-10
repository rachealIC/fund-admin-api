package com.racheal.fundadmin.fundadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateFundDto {

    @NotBlank(message = "Fund name is required")
    @Size(min = 2, max = 100, message = "Fund name must be between 2 and 100 characters")
    @JsonProperty("fund_name")
    private String fundName;



    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @JsonProperty("fund_description")
    private String fundDescription;

    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    @Digits(integer = 17, fraction = 2, message = "Balance must have at most 2 decimal places")
    private BigDecimal balance;

    public CreateFundDto(String fundName, String fundDescription, BigDecimal balance) {
        this.fundName = fundName;
        this.fundDescription = fundDescription;
        this.balance = balance;
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

    @Override
    public String toString() {
        return "CreateFundDto{" +
                "fundName='" + fundName + '\'' +
                ", fundDescription='" + fundDescription + '\'' +
                ", balance=" + balance +
                '}';
    }
}
