package com.example.jpmorganjavatest.util;

import com.example.jpmorganjavatest.enums.ApiClient;
import com.example.jpmorganjavatest.exception.ValidationException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ValidationUtil
{
    public static void validateSources(List<String> sources) throws ValidationException
    {
        for (String source : sources)
        {
            try
            {
                ApiClient.valueOf(source.toUpperCase());
            }
            catch (IllegalArgumentException exception)
            {
                String message = String.format("Validation source: %s is not valid", source);
                throw new ValidationException(message, HttpStatus.BAD_REQUEST.value());
            }
        }
    }
}
