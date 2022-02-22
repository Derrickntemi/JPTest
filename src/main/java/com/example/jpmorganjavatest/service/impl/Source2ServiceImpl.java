package com.example.jpmorganjavatest.service.impl;

import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import com.example.jpmorganjavatest.service.SourceService;
import com.example.jpmorganjavatest.service.client.Source2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("source2")
public class Source2ServiceImpl implements SourceService
{
    private final Source2Client source2Client;


    @Autowired
    public Source2ServiceImpl(Source2Client source2Client)
    {
        this.source2Client = source2Client;
    }


    @Override
    public ValidationApiResponseDto validateAccNo(ValidationApiRequestDto validationApiRequestDto)
    {
        return source2Client.validateAccountNumber(validationApiRequestDto);
    }
}
