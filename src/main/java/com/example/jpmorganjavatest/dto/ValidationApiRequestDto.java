package com.example.jpmorganjavatest.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationApiRequestDto
{
    @NotBlank(message = "account number must not be blank")
    private final String accountNumber;
}
