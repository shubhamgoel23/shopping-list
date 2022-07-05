package com.example.shoppinglist.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public void handleError(HttpServletRequest request) {
        throw new ApplicationException();

    }

}
