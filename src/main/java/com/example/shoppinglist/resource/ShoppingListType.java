package com.example.shoppinglist.resource;

public enum ShoppingListType {
    WISH_LIST("WL"),
    SAVE_FOR_LATER("S4L");

    private String code;

    private ShoppingListType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
