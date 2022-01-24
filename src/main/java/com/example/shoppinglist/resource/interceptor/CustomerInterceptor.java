package com.example.shoppinglist.resource.interceptor;

import com.example.shoppinglist.exception.BusinessException;
import com.example.shoppinglist.exception.BusinessExceptionReason;
import com.example.shoppinglist.resource.context.CustomerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class CustomerInterceptor implements HandlerInterceptor {

    private static final String CUSTOMER_HTTP_HEADER = "X-Customer";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String customerId = request.getHeader(CUSTOMER_HTTP_HEADER);

        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/api/") && ObjectUtils.isEmpty(customerId))
            throw new BusinessException(BusinessExceptionReason.CUSTOMER_NOT_FOUND);

        CustomerContext.setCustomerId(customerId);
        log.info("Customer context: {}", CustomerContext.getCustomerId());

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) {
        log.info("Cleared Customer Context");
        CustomerContext.clear();
    }
}
