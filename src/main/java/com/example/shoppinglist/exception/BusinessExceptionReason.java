package com.example.shoppinglist.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Defines the business exception reasons.
 */
@Getter
@AllArgsConstructor
public enum BusinessExceptionReason implements BusinessExceptionPolicy {

	TODO_NOT_FOUND_BY_EXT_REF("TODO not found based on the given external reference", HttpStatus.NOT_FOUND),
	INVALID_SHOPPING_LIST_ID("Invalid shopping list id", HttpStatus.BAD_REQUEST),
	INVALID_PRODUCT_ID("Invalid product id", HttpStatus.BAD_REQUEST);

	private final String code = this.name();
	private final String message;
	private final HttpStatus httpStatus;

}