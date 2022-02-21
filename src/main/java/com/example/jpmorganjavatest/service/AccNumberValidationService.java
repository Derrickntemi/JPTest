package com.example.jpmorganjavatest.service;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.exception.ApiException;

public interface AccNumberValidationService
{
    AccNumberValidationResponseDto validateAccountNumber(AccNumberValidationRequestDto accNumberValidationRequestDto) throws ApiException;
}
