package com.example.shoppinglist.resource;

public interface ResponseView {

    interface ShoppingListBasic {
    }

    interface ShoppingListItem {
    }

    interface ShoppingListDetailed extends ShoppingListBasic, ShoppingListItem {
    }
}
