package com.example.shoppinglist.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import com.example.shoppinglist.resource.persistance.repository.ShoppingListRepository;
import com.example.shoppinglist.util.Pagination;
import com.example.shoppinglist.util.Response;
import com.example.shoppinglist.util.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingListService {
	
	private final ShoppingListRepository shoppingListRepository;

	public Response<Void, Void> createShoppingList(ShoppingListDto shoppingListDTO) {
		shoppingListRepository.save(ShoppingListEntity.builder()
				.name(shoppingListDTO.name())
				.type(shoppingListDTO.type())
				.build());
		return ResponseBuilder.build(HttpStatus.OK, "Shopping List created");
	}

	public Response<Collection<ShoppingListDto>, Pagination> getShoppingList() {
		return ResponseBuilder.build(
				Collections.unmodifiableCollection(List.of(new ShoppingListDto(null, null, null, null))), null,
				HttpStatus.OK, "Shopping Lists received");
	}

	public Response<ShoppingListDto, Pagination> getShoppingListById(String id) {
		return ResponseBuilder.build(new ShoppingListDto("test", null, null, null), null, HttpStatus.OK,
				"Shopping List received");
	}

	public Response<Void, Void> updateShoppingListById(String id, ShoppingListDto shoppingListDto) {
		return ResponseBuilder.build(HttpStatus.OK, "Shopping List updated");
	}

	public Response<Void, Void> deleteShoppingListById(String id) {
		return ResponseBuilder.build(HttpStatus.OK, "Shopping List deleted");
	}

	public Response<Void, Void> additemInShoppingList(String id, Collection<ItemDto> shoppingListItemDtos) {
		return ResponseBuilder.build(HttpStatus.OK, "Item added to shopping list");
	}

	public Response<Collection<ItemDto>, Pagination> getShoppingListItems(String id) {
		return ResponseBuilder.build(Collections.unmodifiableCollection(List.of(new ItemDto(null, 0))),
				null, HttpStatus.OK, "Shopping List items received");
	}

	public Response<ItemDto, Void> getShoppingListItemByProductId(String id, String productId) {
		return ResponseBuilder.build(new ItemDto(null, 0), null, HttpStatus.OK,
				"Shopping List item received");
	}

	public Response<Void, Void> updateShoppingListItemsByProductId(String id, ItemDto shoppingListItemDto) {
		return ResponseBuilder.build(HttpStatus.OK, "Item updated in shopping list");
	}
}
