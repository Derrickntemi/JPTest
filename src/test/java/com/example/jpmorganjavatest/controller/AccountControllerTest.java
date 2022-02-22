package com.example.jpmorganjavatest.controller;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.service.impl.AccNumberValidationServiceImpl;
import com.example.jpmorganjavatest.testdata.TestDataProvider;
import com.example.jpmorganjavatest.util.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AccountController.class)
@ActiveProfiles("test")
class AccountControllerTest
{
    @MockBean
    private AccNumberValidationServiceImpl accNumberValidationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void givenAnInvalidValidationSourceThenReturnErrorWithStatus400() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "invalidSource");

        // WHEN
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(400);

    }


    @Test
    public void givenARequestContainsABlankAccNoThenReturnErrorWithStatus400() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("", "source1");

        // WHEN
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(400);

    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsNoSourcesAndAValidAccNoThenReturnResponseContainingAllSources() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {new AccNumberValidationResponseDto.ResponseDto("source1", true),
            new AccNumberValidationResponseDto.ResponseDto("source2", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        when(accNumberValidationService.validateAccountNumber(any())).thenReturn(responseDto);
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsAllSourcesAndAValidAccNoThenReturnResponseContainingAllSources() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source1", "source2");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {new AccNumberValidationResponseDto.ResponseDto("source1", true),
            new AccNumberValidationResponseDto.ResponseDto("source2", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        when(accNumberValidationService.validateAccountNumber(any())).thenReturn(responseDto);
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsSource1AndAValidAccNoThenReturnResponseContainingSource1() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source1");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {new AccNumberValidationResponseDto.ResponseDto("source1", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        when(accNumberValidationService.validateAccountNumber(any())).thenReturn(responseDto);
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsSource2AndAValidAccNoThenReturnResponseContainingSource2() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source2");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {new AccNumberValidationResponseDto.ResponseDto("source2", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        when(accNumberValidationService.validateAccountNumber(any())).thenReturn(responseDto);
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsSource1AnInValidAccNoThenReturnCorrectResponse() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source1");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {new AccNumberValidationResponseDto.ResponseDto(
            "source1",
            false)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        when(accNumberValidationService.validateAccountNumber(any())).thenReturn(responseDto);
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsAnInValidSourceThenReturnStatusCode400() throws Exception
    {
        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "1");

        // WHEN
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getContentAsString()).isEqualTo("{\"message\":\"Validation source: 1 is not valid\",\"status\":400}");
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }
}
