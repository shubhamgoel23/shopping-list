package com.example.shoppinglist.resource.persistance;

import com.example.shoppinglist.resource.ShoppingListType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class ShoppingListTypeConverter implements AttributeConverter<ShoppingListType, String> {

    @Override
    public String convertToDatabaseColumn(ShoppingListType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode().toUpperCase();
    }

    @Override
    public ShoppingListType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(ShoppingListType.values())
                .filter(c -> c.getCode().equalsIgnoreCase(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
