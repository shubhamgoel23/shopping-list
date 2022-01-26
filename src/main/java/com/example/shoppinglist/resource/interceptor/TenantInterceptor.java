package com.example.shoppinglist.resource.interceptor;

import com.example.shoppinglist.exception.BusinessException;
import com.example.shoppinglist.exception.BusinessExceptionReason;
import com.example.shoppinglist.resource.context.TenantContext;
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
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HTTP_HEADER = "X-Tenant";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader(TENANT_HTTP_HEADER);

        if (ObjectUtils.isEmpty(tenantId))
            throw new BusinessException(BusinessExceptionReason.TENANT_NOT_FOUND);

        TenantContext.setTenantId(tenantId);
        log.info("Tenant context: {}", TenantContext.getTenantId());

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) {
        log.info("Cleared Tenant Context");
        TenantContext.clear();
    }
}
