package com.example.jpmorganjavatest.service.impl;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto.ResponseDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import com.example.jpmorganjavatest.exception.ApiException;
import com.example.jpmorganjavatest.service.SourceService;
import com.example.jpmorganjavatest.testdata.TestDataProvider;
import feign.RetryableException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class AccNumberValidationServiceImplTest
{

    private Source1ServiceImpl source1Service;

    private Source2ServiceImpl source2Service;

    Map<String, SourceService> validationServices = new HashMap<>();

    @InjectMocks
    private AccNumberValidationServiceImpl accNumberValidationService;


    @BeforeEach
    public void init()
    {
        source1Service = Mockito.mock(Source1ServiceImpl.class);
        source2Service = Mockito.mock(Source2ServiceImpl.class);
        validationServices.put("source1", source1Service);
        validationServices.put("source2", source2Service);
        accNumberValidationService = new AccNumberValidationServiceImpl(validationServices);
    }


    @Test
    public void givenValidAccountNumberAndAllSourcesThenValidateAccNoOnAllClients() throws ApiException
    {
        // GIVEN
        String accNo = "12345678";
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto(accNo, "source1", "source2");
        ValidationApiResponseDto validationApiResponseDto = TestDataProvider.getValidationApiResponseDto(true);
        ResponseDto[] responses = new ResponseDto[] {new ResponseDto("source1", true), new ResponseDto("source2", true)};
        AccNumberValidationResponseDto accNumberValidationResponseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        lenient().doReturn(validationApiResponseDto).when(source1Service).validateAccNo(any());
        lenient().doReturn(validationApiResponseDto).when(source2Service).validateAccNo(any());
        AccNumberValidationResponseDto responseDto = accNumberValidationService.validateAccountNumber(accNumberValidationRequestDto);

        // THEN
        assertThat(responseDto).usingRecursiveComparison().isEqualTo(accNumberValidationResponseDto);
        verify(source1Service, times(1)).validateAccNo(any());
        verify(source2Service, times(1)).validateAccNo(any());
    }


    @Test
    public void givenValidAccountNumberAndEmptySourcesThenValidateAccNoOnAllClients() throws ApiException
    {
        // GIVEN
        String accNo = "12345678";
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto(accNo);
        ValidationApiResponseDto validationApiResponseDto = TestDataProvider.getValidationApiResponseDto(true);
        ResponseDto[] responses = new ResponseDto[] {new ResponseDto("source1", true), new ResponseDto("source2", true)};
        AccNumberValidationResponseDto accNumberValidationResponseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        lenient().doReturn(validationApiResponseDto).when(source1Service).validateAccNo(any());
        lenient().doReturn(validationApiResponseDto).when(source2Service).validateAccNo(any());
        AccNumberValidationResponseDto responseDto = accNumberValidationService.validateAccountNumber(accNumberValidationRequestDto);

        // THEN
        assertThat(responseDto).usingRecursiveComparison().isEqualTo(accNumberValidationResponseDto);
        verify(source1Service, times(1)).validateAccNo(any());
        verify(source2Service, times(1)).validateAccNo(any());
    }


    @Test
    public void givenValidAccountNumberAndSource1ThenValidateAccNoOnSource1Only() throws ApiException
    {
        // GIVEN
        String accNo = "12345678";
        String source = "source1";
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto(accNo, source);
        ValidationApiResponseDto validationApiResponseDto = TestDataProvider.getValidationApiResponseDto(true);
        ResponseDto[] responses = new ResponseDto[] {new ResponseDto(source, true)};
        AccNumberValidationResponseDto accNumberValidationResponseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        lenient().doReturn(validationApiResponseDto).when(source1Service).validateAccNo(any());
        AccNumberValidationResponseDto responseDto = accNumberValidationService.validateAccountNumber(accNumberValidationRequestDto);

        // THEN
        assertThat(responseDto).usingRecursiveComparison().isEqualTo(accNumberValidationResponseDto);
        verify(source1Service, times(1)).validateAccNo(any());
        verifyNoInteractions(source2Service);
    }


    @Test
    public void givenValidAccountNumberAndSource2ThenValidateAccNoOnSource2Only() throws ApiException
    {
        // GIVEN
        String accNo = "12345678";
        String source = "source2";
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto(accNo, source);
        ValidationApiResponseDto validationApiResponseDto = TestDataProvider.getValidationApiResponseDto(true);
        ResponseDto[] responses = new ResponseDto[] {new ResponseDto(source, true)};
        AccNumberValidationResponseDto accNumberValidationResponseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        lenient().doReturn(validationApiResponseDto).when(source2Service).validateAccNo(any());
        AccNumberValidationResponseDto responseDto = accNumberValidationService.validateAccountNumber(accNumberValidationRequestDto);

        // THEN
        assertThat(responseDto).usingRecursiveComparison().isEqualTo(accNumberValidationResponseDto);
        verify(source2Service, times(1)).validateAccNo(any());
        verifyNoInteractions(source1Service);
    }


    @Test
    public void givenValidAccountNumberAndAnErrorOccursWhenInvokingAValidationClientThenThrowApiException()
    {
        // GIVEN
        String accNo = "12345678";
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto(accNo, "source2");

        // WHEN  // THEN
        lenient().doThrow(RetryableException.class).when(source2Service).validateAccNo(any());
        assertThrows(ApiException.class, () -> accNumberValidationService.validateAccountNumber(accNumberValidationRequestDto));
    }
}
