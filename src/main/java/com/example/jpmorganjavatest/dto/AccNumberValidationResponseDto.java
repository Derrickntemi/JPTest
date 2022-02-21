package com.example.jpmorganjavatest.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccNumberValidationResponseDto
{
    private final List<ResponseDto> result;

    @Getter
    @AllArgsConstructor
    public static class ResponseDto
    {
        private String source;
        private boolean isValid;
    }
}
