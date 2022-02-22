package com.example.jpmorganjavatest.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class AccNumberValidationRequestDto
{
    @NotBlank(message = "account number must not be blank")
    private final String accountNumber;
    private List<String> sources;
}
