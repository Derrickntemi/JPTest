package com.example.jpmorganjavatest.integrationtest;

import com.example.jpmorganjavatest.dto.AccNumberValidationRequestDto;
import com.example.jpmorganjavatest.dto.AccNumberValidationResponseDto;
import com.example.jpmorganjavatest.dto.ErrorDto;
import com.example.jpmorganjavatest.testconfig.WireMockInitializer;
import com.example.jpmorganjavatest.testdata.TestDataProvider;
import com.example.jpmorganjavatest.util.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = WireMockInitializer.class)
@AutoConfigureMockMvc
public class ApiIntegrationTest
{
    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @AfterEach
    public void afterEach()
    {
        this.wireMockServer.resetAll();
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsNoSourcesAndAValidAccNoThenReturnResponseContainingAllSources() throws Exception
    {
        this.wireMockServer.stubFor(
            WireMock.post("/v1/api/account/validate")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\"isValid\": true}"))
        );

        this.wireMockServer.stubFor(
            WireMock.post("/v2/api/account/validate")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\"isValid\": true}"))
        );

        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {new AccNumberValidationResponseDto.ResponseDto("source1", true),
            new AccNumberValidationResponseDto.ResponseDto("source2", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
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
        this.wireMockServer.stubFor(
            WireMock.post("/v1/api/account/validate")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\"isValid\": true}"))
        );

        this.wireMockServer.stubFor(
            WireMock.post("/v2/api/account/validate")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\"isValid\": true}"))
        );

        // GIVEN
        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source1", "source2");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {new AccNumberValidationResponseDto.ResponseDto("source1", true),
            new AccNumberValidationResponseDto.ResponseDto("source2", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsSource1AndAValidAccNoThenReturnResponseContainingSource1Response() throws Exception
    {
        // GIVEN
        this.wireMockServer.stubFor(
            WireMock.post("/v1/api/account/validate")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\"isValid\": true}"))
        );

        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source1");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {
            new AccNumberValidationResponseDto.ResponseDto("source1", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestContainsSource2AndAValidAccNoThenReturnResponseContainingSource2Response() throws Exception
    {
        // GIVEN
        this.wireMockServer.stubFor(
            WireMock.post("/v2/api/account/validate")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\"isValid\": true}"))
        );

        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source2");
        AccNumberValidationResponseDto.ResponseDto[] responses = new AccNumberValidationResponseDto.ResponseDto[] {
            new AccNumberValidationResponseDto.ResponseDto("source2", true)};
        AccNumberValidationResponseDto responseDto = TestDataProvider.getAccNumberValidationResponseDto(responses);

        // WHEN
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, responseDto));
    }


    @Test
    @Timed(millis = 2_000)
    public void givenARequestToExternalApiTakesLongerThanASecondThenTimeout() throws Exception
    {
        // GIVEN
        this.wireMockServer.stubFor(
            WireMock.post("/v1/api/account/validate")
                .willReturn(aResponse()
                    .withStatus(500)
                    .withFixedDelay(1001))
        );

        AccNumberValidationRequestDto accNumberValidationRequestDto = TestDataProvider.getAccNoValidationRequestDto("12345678", "source1");
        ErrorDto errorDto = ErrorDto.builder().message("Read timed out executing POST http://localhost:8085/v1/api/account/validate").status(500).build();

        // WHEN
        MvcResult result = mockMvc.perform(post("/v1/accounts/validate").contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.toJson(objectMapper, accNumberValidationRequestDto))).andReturn();

        // THEN
        assertThat(result.getResponse().getStatus()).isEqualTo(500);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(JsonHelper.toJson(objectMapper, errorDto));
    }
}
