package com.example.jpmorganjavatest.service.client;

import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;

public interface ValidationApiClient
{
    String VALIDATE_ACC_NO_ENDPOINT = "/account/validate";
    ValidationApiResponseDto validateAccountNumber(ValidationApiRequestDto validationApiRequestDto);
}
