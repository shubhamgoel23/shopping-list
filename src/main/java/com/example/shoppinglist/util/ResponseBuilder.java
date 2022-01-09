package com.example.shoppinglist.util;

import java.util.Collection;

import org.springframework.http.HttpStatus;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseBuilder {

	public static <T, U> Response<T, U> build(T data, U pagination, HttpStatus status, String message) {
		
		return Response.<T, U>builder().status(status).statusCode(status.value()).message(message).content(data)
				.pagination(pagination).build();
	}

	public static <T> Response<T, Void> build(T data, HttpStatus status, String message) {
		
		return Response.<T, Void>builder().status(status).statusCode(status.value()).message(message).content(data)
				.build();
	}
	
	public static Response<Void, Void> build(HttpStatus status, String message) {

		return Response.<Void, Void>builder().status(status).statusCode(status.value()).message(message).build();
	}

	public static Response<Void, Void> build(Collection<ValidationError> errors, HttpStatus status, String message,
			String reason) {

		return Response.<Void, Void>builder().status(status).statusCode(status.value()).reason(reason)
				.developerMessage(message).errors(errors).build();
	}

	public static Response<Void, Void> build(HttpStatus status, String message, String reason) {

		return Response.<Void, Void>builder().status(status).statusCode(status.value()).reason(reason)
				.developerMessage(message).build();
	}

}
