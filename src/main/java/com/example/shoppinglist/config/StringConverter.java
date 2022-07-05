package com.example.shoppinglist.config;

import org.springframework.core.convert.converter.Converter;


public class StringConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        return source != null ? source.trim() : null;
    }

}