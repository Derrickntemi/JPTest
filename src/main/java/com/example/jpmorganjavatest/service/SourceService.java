package com.example.jpmorganjavatest.service;

import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;

public interface SourceService
{
    ValidationApiResponseDto validateAccNo(ValidationApiRequestDto validationApiRequestDto);
}
