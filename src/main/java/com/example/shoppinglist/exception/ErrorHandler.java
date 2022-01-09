package com.example.shoppinglist.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandler implements ErrorController {

	@RequestMapping("/error")
	public void handleError(HttpServletRequest request) {
		if (request.getAttribute(RequestDispatcher.ERROR_EXCEPTION) != null) {
			throw (RuntimeException) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		}
	}

}
