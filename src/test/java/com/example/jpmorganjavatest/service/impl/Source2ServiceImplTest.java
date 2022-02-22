package com.example.jpmorganjavatest.service.impl;

import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import com.example.jpmorganjavatest.service.client.Source2Client;
import com.example.jpmorganjavatest.testdata.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Source2ServiceImplTest
{
    @Mock
    private Source2Client source2Client;

    @InjectMocks
    private Source2ServiceImpl source2Service;


    @Test
    public void givenAValidAccNumberThenReturnTrue()
    {
        // GIVEN
        String accNo = "12345678";
        ValidationApiRequestDto validationApiRequestDto = TestDataProvider.getValidationApiRequestDto(accNo);
        ValidationApiResponseDto validationApiResponseDto = TestDataProvider.getValidationApiResponseDto(true);

        // WHEN
        when(source2Client.validateAccountNumber(validationApiRequestDto)).thenReturn(validationApiResponseDto);

        ValidationApiResponseDto responseDto = source2Service.validateAccNo(validationApiRequestDto);

        // THEN
        assertTrue(responseDto.getIsValid());
    }


    @Test
    public void givenAnInValidAccNumberThenReturnFalse()
    {
        // GIVEN
        String accNo = "12";
        ValidationApiRequestDto validationApiRequestDto = TestDataProvider.getValidationApiRequestDto(accNo);
        ValidationApiResponseDto validationApiResponseDto = TestDataProvider.getValidationApiResponseDto(false);

        // WHEN
        when(source2Client.validateAccountNumber(validationApiRequestDto)).thenReturn(validationApiResponseDto);

        ValidationApiResponseDto responseDto = source2Service.validateAccNo(validationApiRequestDto);

        // THEN
        assertFalse(responseDto.getIsValid());
    }

}
