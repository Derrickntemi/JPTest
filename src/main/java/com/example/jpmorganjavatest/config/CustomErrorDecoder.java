package com.example.jpmorganjavatest.config;

import com.example.jpmorganjavatest.exception.ApiException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder
{
    @Override
    public Exception decode(String methodKey, Response response)
    {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        return new ApiException(exception.getMessage());
    }
}
