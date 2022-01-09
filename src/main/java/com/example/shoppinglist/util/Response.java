package com.example.shoppinglist.util;

import static com.example.shoppinglist.util.HelperClass.currentTimeMillis;
import static com.example.shoppinglist.util.HelperClass.currentTraceId;
import static com.example.shoppinglist.util.HelperClass.requestMethod;
import static com.example.shoppinglist.util.HelperClass.requestUrl;

import java.util.Collection;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

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

	protected T content;

	protected Collection<ValidationError> errors;
}
