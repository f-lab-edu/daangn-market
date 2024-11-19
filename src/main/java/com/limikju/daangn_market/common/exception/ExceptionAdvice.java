package com.limikju.daangn_market.common.exception;

import com.limikju.daangn_market.common.code.ErrorReasonDTO;
import com.limikju.daangn_market.common.code.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = GeneralException.class)
  public ResponseEntity onThrowException(GeneralException generalException,
      HttpServletRequest request) {
    ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
    return handleExceptionInternal(generalException, errorReasonHttpStatus, null, request);
  }

  private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
      HttpHeaders headers, HttpServletRequest request) {

    ResponseEntity<Object> body = new ResponseEntity<>(reason.getMessage(), reason.getHttpStatus());

    WebRequest webRequest = new ServletWebRequest(request);
    return super.handleExceptionInternal(
        e,
        body,
        headers,
        reason.getHttpStatus(),
        webRequest
    );
  }
}
