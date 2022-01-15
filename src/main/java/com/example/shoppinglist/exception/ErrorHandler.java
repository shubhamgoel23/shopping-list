package com.example.shoppinglist.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public void handleError(HttpServletRequest request) {
        if (request.getAttribute(RequestDispatcher.ERROR_EXCEPTION) != null) {
            throw (RuntimeException) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        }
        if (request.getAttribute(RequestDispatcher.ERROR_MESSAGE) != null) {
            throw new RuntimeException(String.valueOf(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)));
        }
    }

}
