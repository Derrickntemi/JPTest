package com.example.jpmorganjavatest.service.impl;

import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import com.example.jpmorganjavatest.service.SourceService;
import com.example.jpmorganjavatest.service.client.Source1Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("source1")
public class Source1ServiceImpl implements SourceService
{
    private final Source1Client source1Client;


    @Autowired
    public Source1ServiceImpl(Source1Client source1Client)
    {
        this.source1Client = source1Client;
    }


    @Override
    public ValidationApiResponseDto validateAccNo(ValidationApiRequestDto validationApiRequestDto)
    {
        return source1Client.validateAccountNumber(validationApiRequestDto);
    }
}
