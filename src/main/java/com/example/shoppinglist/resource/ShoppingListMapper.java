package com.example.shoppinglist.resource;

import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ShoppingListMapper {

    //	@Mapping(target = "items", ignore = true)
    List<ShoppingListDto> fromShoppingListEntity(List<ShoppingListEntity> entities);

    //	@Mapping(target = "items", ignore = true)
    ShoppingListDto fromShoppingListEntity(ShoppingListEntity entities);

    List<ItemEntity> fromItemDto(List<ItemDto> itemDto);

    List<ItemDto> fromItemEntity(List<ItemEntity> itemEntities);

    ItemDto fromItemEntity(ItemEntity itemEntities);
}
