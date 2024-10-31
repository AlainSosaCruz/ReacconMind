package com.reacconmind.reacconmind.exception;

import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(
        ExceptionHandlerAdvice.class
    );

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(
        NoSuchElementException e
    ) {
        logger.error("No such element: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            "The requested item is not registered"
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            e.getMessage()
        );
    }

    /*@ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(
        NullPointerException e
    ) {
        logger.error("Null pointer exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            "A null pointer exception occurred"
        );
    }*/

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<String> handleArrayIndexOutOfBoundsException(
        ArrayIndexOutOfBoundsException e
    ) {
        logger.error("Array index out of bounds: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            "Array index out of bounds"
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            "An unexpected error occurred"
        );
    }

    
}
