package com.racheal.fundadmin.fundadmin.web;

import com.racheal.fundadmin.fundadmin.dto.CreateFundDto;
import com.racheal.fundadmin.fundadmin.dto.FundResponseDto;
import com.racheal.fundadmin.fundadmin.service.FundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class FundController {


    private  final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @PostMapping("/funds")
    public ResponseEntity<FundResponseDto> fund(@Valid @RequestBody CreateFundDto createFundDto) {
        FundResponseDto fundResponseDto = fundService.saveFund(createFundDto);
        return new ResponseEntity<>(fundResponseDto, HttpStatus.CREATED);
    }



    @GetMapping("/funds/{name}")
    public Optional<FundResponseDto> getFundByName(@PathVariable("name") String fundName) {
        return fundService.getFundByName(fundName);

    }

}
