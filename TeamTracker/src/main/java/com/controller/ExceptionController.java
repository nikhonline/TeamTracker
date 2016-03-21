package com.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.exeption.GenericException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({ Throwable.class })
    protected ResponseEntity<Object> handleAllExceptions(RuntimeException e, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, new GenericException(e), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}