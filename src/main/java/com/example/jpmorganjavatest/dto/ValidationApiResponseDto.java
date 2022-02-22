package com.example.jpmorganjavatest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class ValidationApiResponseDto
{
    private Boolean isValid;
}
