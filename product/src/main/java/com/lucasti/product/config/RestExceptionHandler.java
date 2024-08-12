package com.lucasti.product.config;

import com.lucasti.product.exceptions.DepartamentNotFoundException;
import com.lucasti.product.exceptions.ProductNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {


    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<CustomErrorDTO> illegalArgumentException(IllegalArgumentException illegalArgumentException){
        var customError =
                new CustomErrorDTO(illegalArgumentException.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(customError);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ProductNotFoundException.class)
//    private ResponseEntity<CustomErrorDTO> productNotFoundHandler(ProductNotFoundException productNotFoundException){
//        var customError =
//                new CustomErrorDTO(productNotFoundException.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
//    }
//
//    @ExceptionHandler(DepartamentNotFoundException.class)
//    private ResponseEntity<CustomErrorDTO> departamentNotFound(DepartamentNotFoundException departamentNotFoundException){
//        var customError =
//                new CustomErrorDTO(departamentNotFoundException.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
//    }
//
//
//
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<String> handleValidationExceptions11(BadRequestException ex) {
//
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }


//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status,
//                                                                  WebRequest request) {
//        Map<String, String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .collect(Collectors.toMap(
//                        FieldError::getField,
//                        FieldError::getDefaultMessage
//                ));
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }



}
