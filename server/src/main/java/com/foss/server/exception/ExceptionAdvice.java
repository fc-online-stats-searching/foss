package com.foss.server.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.foss.server.exception.ExceptionType.*;


@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(MISSING_REQUEST_PARAMETER.getCode(), MISSING_REQUEST_PARAMETER.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse> exception(Exception e) {
        return ResponseEntity.internalServerError().body(new ExceptionResponse(500, e.getMessage()));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> memberNotFoundException(HttpServletRequest request, Exception e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(NOT_FOUND_MEMBER_ID.getCode(), NOT_FOUND_MEMBER_ID.getMessage()));
    }

    @ExceptionHandler(ManyRequestException.class)
    protected ResponseEntity<ExceptionResponse> manyRequestException(HttpServletRequest request, Exception e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(MANY_REQUEST_API.getCode(), MANY_REQUEST_API.getMessage()));
    }

    @ExceptionHandler(MatchIdNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> matchIdNotFoundException(HttpServletRequest request, Exception e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(NOT_FOUND_MATCH_ID.getCode(), NOT_FOUND_MATCH_ID.getMessage()));
    }


    @ExceptionHandler(NicknameNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> nicknameNotFoundException(HttpServletRequest request, Exception e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(NOT_FOUND_MEMBER_NICKNAME.getCode(), NOT_FOUND_MEMBER_NICKNAME.getMessage()));
    }

}

