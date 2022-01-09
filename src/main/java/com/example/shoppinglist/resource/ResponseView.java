package com.example.shoppinglist.resource;

public interface ResponseView {

	public static interface ShoppingListBasic {
	}

	public static interface ShoppingListItem {
	}

	public static interface ShoppingListDetailed extends ShoppingListBasic, ShoppingListItem {
	}
}
