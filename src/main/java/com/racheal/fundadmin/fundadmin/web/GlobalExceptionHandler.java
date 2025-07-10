package com.racheal.fundadmin.fundadmin.web;

import com.racheal.fundadmin.fundadmin.resources.ApiResponse;
import com.racheal.fundadmin.fundadmin.resources.exceptions.DuplicateResourceException;
import com.racheal.fundadmin.fundadmin.resources.exceptions.InsufficientBalanceException;
import com.racheal.fundadmin.fundadmin.resources.exceptions.ResourceNotFoundException;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationErrors(MethodArgumentNotValidException exception) {
        String errors = exception.getBindingResult()
                .getFieldErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(errors, null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(exception.getMessage(), null));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoFound(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(exception.getMessage(), null));
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicate(DuplicateRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<String>> handleInsufficientBalance(InsufficientBalanceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(exception.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(exception.getMessage(), null));
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceDuplicates(DuplicateResourceException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(exception.getMessage(), null));
    }
}
