package com.example.xeva.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler{


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<HashMap> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        HttpHeaders headers = new HttpHeaders();
        HashMap<String,String> fieldsErrorMessage = new HashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for(FieldError fieldError: fieldErrors){
            fieldsErrorMessage.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return new ResponseEntity(fieldsErrorMessage, headers, HttpStatus.BAD_REQUEST);
    }

}
