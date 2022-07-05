package com.example.shoppinglist.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;

public class CustomStringDeserializer extends StringDeserializer {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return parser.hasToken(VALUE_STRING) ? parser.getText().trim() : null;
    }

}