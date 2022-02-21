package com.example.jpmorganjavatest.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationApiResponseDto
{
    private final boolean isValid;
}
