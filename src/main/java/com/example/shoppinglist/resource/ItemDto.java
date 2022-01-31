package com.example.shoppinglist.resource;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ItemDto(

        // @formatter:off
        @JsonView({
                RequestView.ShoppingListAddItem.class,
                ResponseView.ShoppingListItem.class})
        @NotBlank(groups = {
                RequestView.ShoppingListAddItem.class})
        @Size(groups = {
                RequestView.ShoppingListAddItem.class}, max = 36)
        String productId,

        @JsonView({RequestView.ShoppingListAddItem.class, RequestView.ShoppingListUpdateItem.class,
                ResponseView.ShoppingListItem.class})
        @Min(value = 0, groups = {RequestView.ShoppingListAddItem.class,
                RequestView.ShoppingListUpdateItem.class})
        Integer quantity
        // @formatter:on
) {
    public ItemDto() {
        this(null, 0);
    }
}
