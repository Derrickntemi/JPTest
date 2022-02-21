package com.example.jpmorganjavatest.service.impl;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto.ResponseDto;
import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import com.example.jpmorganjavatest.exception.ApiException;
import com.example.jpmorganjavatest.service.AccNumberValidationService;
import com.example.jpmorganjavatest.service.client.ValidationApiClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccNumberValidationServiceImpl implements AccNumberValidationService
{

    private final Map<String, ValidationApiClient> validationApisMap;


    @Autowired
    public AccNumberValidationServiceImpl(Map<String, ValidationApiClient> validationApisMap)
    {
        this.validationApisMap = validationApisMap;
    }


    @Override
    public AccNumberValidationResponseDto validateAccountNumber(AccNumberValidationRequestDto accNumberValidationRequestDto) throws ApiException
    {
        try
        {
            List<ValidationApiClient> validationApiClients = retrieveValidationApiClients(accNumberValidationRequestDto.getSources());
            List<ResponseDto> responses = validationApiClients.stream().map(client -> invokeValidationApis(client, accNumberValidationRequestDto)).collect(Collectors.toList());
            return AccNumberValidationResponseDto.builder().result(responses).build();
        }
        catch (Exception exception)
        {
            log.error("An error occurred: {} ", exception.getMessage(), exception);
            throw new ApiException(exception.getMessage());
        }
    }


    private List<ValidationApiClient> retrieveValidationApiClients(List<String> sources)
    {
        if (sources.isEmpty())
        {
            return new ArrayList<>(validationApisMap.values());
        }

        return validationApisMap.entrySet()
            .stream()
            .filter(entry -> sources.contains(getClientName(entry.getKey())))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }


    private String getClientName(String fullClassPath)
    {
        return fullClassPath.substring(fullClassPath.lastIndexOf('.') + 1).toLowerCase();
    }


    private ResponseDto invokeValidationApis(ValidationApiClient validationApiClient, AccNumberValidationRequestDto accNumberValidationRequestDto)
    {
        String accNo = accNumberValidationRequestDto.getAccountNumber();
        ValidationApiRequestDto requestDto = ValidationApiRequestDto.builder().accountNumber(accNo).build();
        String clientName = validationApisMap.entrySet().stream().filter(entry -> entry.getValue() == validationApiClient).map(entry -> getClientName(entry.getKey())).findFirst().get().toLowerCase();
        log.info("Invoking external api {} to validate acc no {}", clientName, accNo);
        ValidationApiResponseDto validationApiResponseDto = validationApiClient.validateAccountNumber(requestDto);
        return new ResponseDto(clientName, validationApiResponseDto.isValid());
    }

}
