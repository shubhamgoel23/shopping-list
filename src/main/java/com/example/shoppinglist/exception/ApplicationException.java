package com.example.shoppinglist.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * Defines the policy contract for custom application exceptions which need to adhere to it for providing a standardized
 * behavior.
 */
@Getter
public class ApplicationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
}
