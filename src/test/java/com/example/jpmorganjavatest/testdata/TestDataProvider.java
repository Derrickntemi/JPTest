package com.example.jpmorganjavatest.testdata;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto.ResponseDto;
import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataProvider
{
    public static AccNumberValidationRequestDto getAccNoValidationRequestDto(String accNo, String... sources)
    {
        List<String> clientNames = new ArrayList<>(Arrays.asList(sources));
        return AccNumberValidationRequestDto.builder().accountNumber(accNo).sources(clientNames).build();
    }


    public static ValidationApiRequestDto getValidationApiRequestDto(String accNo)
    {
        return ValidationApiRequestDto.builder().accountNumber(accNo).build();
    }


    public static ValidationApiResponseDto getValidationApiResponseDto(boolean isValid)
    {
        return ValidationApiResponseDto.builder().isValid(isValid).build();
    }


    public static AccNumberValidationResponseDto getAccNumberValidationResponseDto(ResponseDto... response)
    {
        List<ResponseDto> sourcesResponse = Arrays.asList(response);
        return AccNumberValidationResponseDto.builder().result(sourcesResponse).build();
    }
}
