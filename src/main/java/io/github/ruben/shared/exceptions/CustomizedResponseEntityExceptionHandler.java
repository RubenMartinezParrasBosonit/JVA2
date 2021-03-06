package io.github.ruben.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public final ResponseEntity<CustomError> handleNotFoundException(IdNotFoundException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnprocesableException.class)
    public final ResponseEntity<CustomError> handleUnprocesableException(UnprocesableException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public final ResponseEntity<CustomError> handleStorageFileNotFoundException(StorageFileNotFoundException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StorageException.class)
    public final ResponseEntity<CustomError> handleStorageException(StorageException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
