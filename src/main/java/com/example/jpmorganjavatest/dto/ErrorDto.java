package com.example.jpmorganjavatest.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDto
{
    private final String message;
    private final int status;
}
