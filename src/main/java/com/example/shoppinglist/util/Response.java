package com.example.shoppinglist.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Collection;

import static com.example.shoppinglist.util.HelperClass.*;

@Data
@SuperBuilder
public class Response<T> {

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected long timeStamp = currentTimeMillis.getAsLong();

    @Builder.Default
    protected String traceId = currentTraceId.get().orElse(null);

    @Builder.Default
    protected String method = requestMethod.get().orElse(null);

    @Builder.Default
    protected String path = requestUrl.get().orElse(null);

    protected HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected int statusCode;

    protected String reason;

    protected String message;

    protected String developerMessage;

    protected T data;

    protected Collection<ValidationError> errors;
}
