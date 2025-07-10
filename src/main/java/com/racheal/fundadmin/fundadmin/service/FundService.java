package com.racheal.fundadmin.fundadmin.service;

import com.racheal.fundadmin.fundadmin.dto.CreateFundDto;
import com.racheal.fundadmin.fundadmin.dto.FundResponseDto;
import com.racheal.fundadmin.fundadmin.models.Fund;
import com.racheal.fundadmin.fundadmin.models.Transaction;
import com.racheal.fundadmin.fundadmin.repository.FundRepository;
import com.racheal.fundadmin.fundadmin.resources.exceptions.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class FundService {


    private final FundRepository fundRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public  FundService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    public List<Fund> getAllFunds() {
        return List.of();
    }

    public Optional<FundResponseDto> getFundByName(String fundName) {
        logger.info("Get fund by name: {}", fundName);
        return fundRepository.findByFundName(fundName).map(FundResponseDto::fromEntity);
    }



    public FundResponseDto saveFund(CreateFundDto createFundDto) {
        boolean fundExist = fundRepository.findByFundName(createFundDto.getFundName()).isPresent();
        if (fundExist) {
            throw new DuplicateResourceException("A fund with the name '" + createFundDto.getFundName() + "' already exists.");
        }
        Fund fund = new Fund(
                createFundDto.getFundName(),
                createFundDto.getFundDescription(),
                createFundDto.getBalance()
        );



        Fund savedFund = fundRepository.save(fund);
        logger.info("Save fund: {}", fund);
        return FundResponseDto.fromEntity(savedFund);

    }
}
