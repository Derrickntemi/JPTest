package com.example.jpmorganjavatest.service.impl;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto.ResponseDto;
import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import com.example.jpmorganjavatest.exception.ApiException;
import com.example.jpmorganjavatest.service.AccNumberValidationService;
import com.example.jpmorganjavatest.service.SourceService;
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

    private final Map<String, SourceService> validationServiceMap;


    @Autowired
    public AccNumberValidationServiceImpl(Map<String, SourceService> validationServiceMap)
    {
        this.validationServiceMap = validationServiceMap;
    }


    @Override
    public AccNumberValidationResponseDto validateAccountNumber(AccNumberValidationRequestDto accNumberValidationRequestDto) throws ApiException
    {
        try
        {
            List<SourceService> validationApiClients = retrieveValidationApiClients(accNumberValidationRequestDto.getSources());
            List<ResponseDto> responses = validationApiClients.stream().map(client -> invokeValidationApis(client, accNumberValidationRequestDto)).collect(Collectors.toList());
            return AccNumberValidationResponseDto.builder().result(responses).build();
        }
        catch (Exception exception)
        {
            log.error("An error occurred: {} ", exception.getMessage(), exception);
            throw new ApiException(exception.getMessage());
        }
    }


    private List<SourceService> retrieveValidationApiClients(List<String> sources)
    {
        if (sources.isEmpty())
        {
            return new ArrayList<>(validationServiceMap.values());
        }

        return sources.stream().map(validationServiceMap::get).collect(Collectors.toList());
    }


    private ResponseDto invokeValidationApis(SourceService sourceService, AccNumberValidationRequestDto accNumberValidationRequestDto)
    {
        String accNo = accNumberValidationRequestDto.getAccountNumber();
        ValidationApiRequestDto requestDto = ValidationApiRequestDto.builder().accountNumber(accNo).build();
        String clientName = validationServiceMap.entrySet().stream().filter(entry -> entry.getValue() == sourceService).map(Map.Entry::getKey).findFirst().get();
        log.info("Invoking external api {} to validate acc no {}", clientName, accNo);
        ValidationApiResponseDto validationApiResponseDto = sourceService.validateAccNo(requestDto);
        return new ResponseDto(clientName, validationApiResponseDto.isValid());
    }

}
