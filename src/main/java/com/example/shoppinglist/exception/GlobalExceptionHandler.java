package com.example.shoppinglist.exception;

import com.example.shoppinglist.util.LoggerHelper;
import com.example.shoppinglist.util.Response;
import com.example.shoppinglist.util.ResponseBuilder;
import com.example.shoppinglist.util.ValidationError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.RequestDispatcher;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.shoppinglist.util.HelperClass.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ApplicationException.class})
    protected ResponseEntity<Object> handleApplicationException(final ApplicationException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.resolve((Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, 0));

        Object body = Response.<Void>builder()
                .path((String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI, 0))
                .status(status)
                .statusCode(status.value())
                .reason((String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE, 0))
                .developerMessage((String) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION, 0))
                .build();

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler({BusinessException.class})
    protected ResponseEntity<Object> handleBusinessException(final BusinessException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Object body = ResponseBuilder.build(ex.getHttpStatus(), ex.getLocalizedMessage(), ex.getCode());
        return handleExceptionInternal(ex, body, headers, ex.getHttpStatus(), request);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleGenericException(final Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (ObjectUtils.isEmpty(body))
            log.error("Request {} {} failed with exception reason: {}", requestMethod.get().orElse("null"),
                    requestUrl.get().orElse("null"), ex.getMessage());
        else {
            Response response = (Response) body;
            log.error("Request {} {} failed with reason: {}", response.getMethod(),
                    response.getPath(), response.getReason());
        }
        log.error(LoggerHelper.printTop10StackTrace(ex));

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
        }
        Throwable root = ExceptionUtils.getRootCause(ex);

        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        String msg = "Oops, Something went wrong!";
        String reason = root.getClass().getSimpleName();
        if (root instanceof BusinessException exception) {
            reason = exception.getCode();
            msg = exception.getMessage();
        }

        body = ObjectUtils.isEmpty(body)
                ? ResponseBuilder.build(status, msg, reason)
                : body;

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
//                .headers(headers)
                .cacheControl(CacheControl
                        .maxAge(1, TimeUnit.MINUTES)
                        .mustRevalidate()
                        .cachePrivate()
                        .noTransform()
                        .staleIfError(1, TimeUnit.MINUTES)
                        .staleWhileRevalidate(1, TimeUnit.MINUTES))
                .body(body);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
     * validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
     *                validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ValidationError> errors = addValidationErrors(ex.getBindingResult().getFieldErrors());
        errors.addAll(addValidationErrors(ex.getBindingResult().getGlobalErrors()));
        Object body = ResponseBuilder.build(errors, status, "Validation error", ex.getClass().getSimpleName());

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
     * fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ValidationError> errors = addValidationErrors(ex.getConstraintViolations());
        Object body = ResponseBuilder.build(errors, status, "Validation error", ex.getClass().getSimpleName());

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    public List<ValidationError> addValidationErrors(List<? extends ObjectError> fieldErrors) {
        return collectionAsStream(fieldErrors).map(this::addValidationError).collect(Collectors.toList());

    }

    private ValidationError addValidationError(String object, String message) {
        return ValidationError.builder().object(object).message(message).build();
    }

    private ValidationError addValidationError(String object, String field, Object rejectedValue, String message) {
        return ValidationError.builder().object(object).field(field).rejectedValue(rejectedValue).message(message)
                .build();
    }

    private ValidationError addValidationError(ObjectError objectError) {
        if (objectError instanceof FieldError fieldError) {
            return this.addValidationError(fieldError.getObjectName(), fieldError.getField(),
                    fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        } else {
            return this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
        }

    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when
     * a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private ValidationError addValidationError(ConstraintViolation<?> cv) {
        return this.addValidationError(cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
    }

    public List<ValidationError> addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        return collectionAsStream(constraintViolations).map(this::addValidationError).toList();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Object body = null;
        if (ex.getCause() instanceof InvalidFormatException ifx) {
            if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
                var errorMsg = String.format("Invalid value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size() - 1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
                body = ResponseBuilder.build(status, errorMsg, ifx.getClass().getSimpleName());
            }
        }

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

}
