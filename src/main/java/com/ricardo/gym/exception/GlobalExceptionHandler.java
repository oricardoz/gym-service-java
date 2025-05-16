package com.ricardo.gym.exception;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception e, HttpServletRequest request) {
        logger.error("Unhandled exception at path {}: {}", request.getRequestURI(), e.getMessage(), e);

        ErrorResponse error = ErrorResponse.builder()
                .message("Error Processing Request")
                .error("Server error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        logger.warn("Resource not found at path {}: {}", request.getRequestURI(), e.getMessage(), e);

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Resource Not Found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.warn("Validation error at path {}: {}", request.getRequestURI(), e.getMessage(), e);

        ValidationErrorResponse error = new ValidationErrorResponse();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setError("Validation Error");
        error.setPath(request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                error.addError(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(TokenCreationException.class)
    public ResponseEntity<ErrorResponse> handleTokenCreating(TokenCreationException e, HttpServletRequest request) {
        logger.error("Token creation error at path {}: {}", request.getRequestURI(), e.getMessage(), e);

        ErrorResponse error = ErrorResponse.builder()
                .message(e.getMessage())
                .error("Server error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationCreating(TokenValidationException e, HttpServletRequest request) {
        logger.error("Token validation error at path {}: {}", request.getRequestURI(), e.getMessage(), e);

        ErrorResponse error = ErrorResponse.builder()
                .message("Invalid token")
                .error("Unauthorized")
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException e, HttpServletRequest request) {
        logger.warn("Invalid credentials at {}: {}", request.getRequestURI(), e.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .message(e.getMessage())
                .error("Unauthorized")
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException e, HttpServletRequest request) {
        logger.warn("Attempt to register with existing email at {}: {}", request.getRequestURI(), e.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .message(e.getMessage())
                .error("Conflict")
                .status(HttpStatus.CONFLICT.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e, HttpServletRequest request) {
        logger.warn("User not found at {}: {}", request.getRequestURI(), e.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .message(e.getMessage())
                .error("Invalid Arguments")
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
