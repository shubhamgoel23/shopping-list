package com.example.shoppinglist.resource;

import java.util.Collection;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;

public record ShoppingListDto(

        // @formatter:off

        @JsonView( {
                ResponseView.ShoppingListBasic.class }) String id,

        @JsonView({ RequestView.ShoppingListCreate.class, RequestView.ShoppingListUpdate.class,
                ResponseView.ShoppingListBasic.class }) @NotBlank(groups = { RequestView.ShoppingListCreate.class,
                        RequestView.ShoppingListUpdate.class }) String name,

        @JsonView({ RequestView.ShoppingListCreate.class,
                ResponseView.ShoppingListBasic.class }) @NotBlank(groups = {
                        RequestView.ShoppingListCreate.class }) String type,

        @JsonView({ ResponseView.ShoppingListDetailed.class })
        Collection<ItemDto> items
    // @formatter:on
    ){
}
