package com.example.shoppinglist.util;

import com.google.common.base.CharMatcher;
import lombok.experimental.UtilityClass;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.*;
import java.util.stream.Stream;

@UtilityClass
public class HelperClass {

    public static final LongSupplier currentTimeMillis = System::currentTimeMillis;

    public static final Supplier<Optional<HttpServletRequest>> httpServletRequest = () -> Optional
            .ofNullable(RequestContextHolder.getRequestAttributes()).filter(ServletRequestAttributes.class::isInstance)
            .map(ServletRequestAttributes.class::cast).map(ServletRequestAttributes::getRequest);

    public static final Supplier<Optional<String>> requestUrl = () -> httpServletRequest.get()
            .map(HttpServletRequest::getRequestURI);

    public static final Supplier<Optional<String>> requestMethod = () -> httpServletRequest.get()
            .map(HttpServletRequest::getMethod);

    public static final Supplier<Optional<String>> currentTraceId = () -> Optional.ofNullable(MDC.get("traceId"));

    public static final UnaryOperator<String> sanitizeString = source -> {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        return policy.sanitize(source);
    };

    public static final UnaryOperator<String> trimString = source -> source != null ? source.trim() : null;

    public static final Predicate<String> isStringWithoutSpecialCharacters = source -> CharMatcher
            // .anyOf("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")
            .anyOf("'\"`;*%_=&\\\\|*?~<>^()[]{}$\\n\\r").matchesNoneOf(source);
//    public static final Supplier<String> uuid = () -> UUID.randomUUID().toString();

    public static final Supplier<String> uuid = () -> SecureRandomString.generate();

    public static final Function<Object, String> cleanString = source -> {
        if (ObjectUtils.isEmpty(source))
            return null;
        return HtmlUtils.htmlEscape(source.toString()).replace('\t', '-').replace('\n', '-').replace('\r', '-');
    };

    public static final <T> Stream<T> collectionAsStream(Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }
}
