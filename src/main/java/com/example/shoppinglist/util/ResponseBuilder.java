package com.example.shoppinglist.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@UtilityClass
public class ResponseBuilder {

    public static <T> ResponseEntity<Response<T>> build(T data, HttpStatus status, String message) {

        return ResponseEntity.ok(
                Response.<T>builder().status(status).statusCode(status.value()).message(message).data(data).build());
    }

    public static ResponseEntity<Response<Void>> build(HttpStatus status, String message) {

        return ResponseEntity
                .ok(Response.<Void>builder().status(status).statusCode(status.value()).message(message).build());
    }

    public static Response<Void> build(Collection<ValidationError> errors, HttpStatus status, String message,
                                       String reason) {

        return Response.<Void>builder().status(status).statusCode(status.value()).reason(reason)
                .developerMessage(message).errors(errors).build();
    }

    public static Response<Void> build(HttpStatus status, String message, String reason) {

        return Response.<Void>builder().status(status).statusCode(status.value()).reason(reason)
                .developerMessage(message).build();
    }

}
