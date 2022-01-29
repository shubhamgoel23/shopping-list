package com.example.shoppinglist.resource;

import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ShoppingListMapper {

    @Mapping(target = "id", source = "listId")
    List<ShoppingListDto> fromShoppingListEntity(List<ShoppingListEntity> entities);

    @Mapping(target = "id", source = "listId")
    ShoppingListDto fromShoppingListEntity(ShoppingListEntity entities);

    List<ItemEntity> fromItemDto(List<ItemDto> itemDto);

    List<ItemDto> fromItemEntity(List<ItemEntity> itemEntities);

    ItemDto fromItemEntity(ItemEntity itemEntities);
}
