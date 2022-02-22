package com.example.jpmorganjavatest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationApiResponseDto
{
    private final boolean isValid;
}
