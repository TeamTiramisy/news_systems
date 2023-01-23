package ru.rndev.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rndev.dto.ErrorDto;
import ru.rndev.exception.NotAccessRightsException;
import ru.rndev.exception.ResourceNotFoundException;
import ru.rndev.util.Constant;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(exception.getMessage())
                        .errorCode(exception.getErrorCode())
                        .build());
    }

    @ExceptionHandler(NotAccessRightsException.class)
    public ResponseEntity<ErrorDto> forbiddenException(NotAccessRightsException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorDto.builder()
                        .message(exception.getMessage())
                        .errorCode(exception.getErrorCode())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> notValid(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(exception.getMessage())
                                .errorCode(Constant.ERROR_CODE_VALID)
                                .build());
    }

}
