package com.example.jpmorganjavatest.service.client;

import com.example.jpmorganjavatest.config.FeignConfig;
import com.example.jpmorganjavatest.dto.ValidationApiRequestDto;
import com.example.jpmorganjavatest.dto.ValidationApiResponseDto;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${source2.name}", url = "${source2.url}", configuration = FeignConfig.class)
public interface Source2 extends ValidationApiClient
{
    @PostMapping(VALIDATE_ACC_NO_ENDPOINT)
    ValidationApiResponseDto validateAccountNumber(@RequestBody @Valid ValidationApiRequestDto validationApiRequestDto);
}
