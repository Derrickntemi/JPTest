package com.example.jpmorganjavatest.controller;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.exception.ApiException;
import com.example.jpmorganjavatest.exception.ValidationException;
import com.example.jpmorganjavatest.service.AccNumberValidationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.jpmorganjavatest.util.ValidationUtil.validateSources;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController
{
    private final AccNumberValidationService accNumberValidationService;


    @Autowired
    public AccountController(AccNumberValidationService accNumberValidationService)
    {
        this.accNumberValidationService = accNumberValidationService;
    }


    @PostMapping("/validate")
    public AccNumberValidationResponseDto validateAccountNumber(@RequestBody @Valid AccNumberValidationRequestDto accNumberValidationRequestDto) throws ValidationException, ApiException
    {
        validateSources(accNumberValidationRequestDto.getSources());
        return accNumberValidationService.validateAccountNumber(accNumberValidationRequestDto);
    }
}
