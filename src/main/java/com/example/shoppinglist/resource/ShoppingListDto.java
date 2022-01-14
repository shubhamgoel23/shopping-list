package com.example.shoppinglist.resource;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotBlank;

public record ShoppingListDto(

// @formatter:off

        @JsonView({ResponseView.ShoppingListBasic.class})
        String id,

        @JsonView({RequestView.ShoppingListCreate.class, RequestView.ShoppingListUpdate.class, ResponseView.ShoppingListBasic.class})
        @NotBlank(groups = {RequestView.ShoppingListCreate.class, RequestView.ShoppingListUpdate.class})
        String name,

        @JsonView({RequestView.ShoppingListCreate.class, ResponseView.ShoppingListBasic.class})
        @NotBlank(groups = {RequestView.ShoppingListCreate.class})
        String type

//        @JsonView({ ResponseView.ShoppingListDetailed.class })
//        Map<String,ItemDto> shoppingListItemMap
        // @formatter:on
) {
    public ShoppingListDto() {
        this(null, null, null);
    }
}
