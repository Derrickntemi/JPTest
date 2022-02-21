package com.example.jpmorganjavatest.service.client;

import com.example.jpmorganjavatest.config.FeignConfig;
import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${source1.name}", url = "${source1.url}", configuration = FeignConfig.class)
public interface Source1 extends ValidationApiClient
{
    @PostMapping(VALIDATE_ACC_NO_ENDPOINT)
    ValidationApiResponseDto validateAccountNumber(@RequestBody @Valid ValidationApiRequestDto validationApiRequestDto);
}
