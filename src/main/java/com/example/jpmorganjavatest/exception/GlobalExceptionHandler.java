package com.example.jpmorganjavatest.exception;

import com.example.jpmorganjavatest.dto.ErrorDto;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex)
    {
        ErrorDto error = ErrorDto.builder().message(ex.getMessage()).status(HttpServletResponse.SC_BAD_REQUEST).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApiException(ApiException ex)
    {
        ErrorDto error = ErrorDto.builder().message(ex.getMessage()).status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
