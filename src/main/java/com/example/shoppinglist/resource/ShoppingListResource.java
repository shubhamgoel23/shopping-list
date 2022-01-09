package com.example.shoppinglist.resource;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppinglist.resource.RequestView.ShoppingListAddItem;
import com.example.shoppinglist.resource.RequestView.ShoppingListCreate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdateItem;
import com.example.shoppinglist.util.Pagination;
import com.example.shoppinglist.util.Response;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ShoppingListResource {

	private final ShoppingListService shoppingListService;

	@PostMapping("/shopping-list")
	public ResponseEntity<Response<Void, Void>> createShoppingList(@Validated(ShoppingListCreate.class) @JsonView({
			ShoppingListCreate.class }) @RequestBody ShoppingListDto shoppingListDTO) {
		return ResponseEntity.ok(shoppingListService.createShoppingList(shoppingListDTO));
	}

	@GetMapping("/shopping-list")
	@JsonView({ ResponseView.ShoppingListBasic.class })
	public ResponseEntity<Response<Collection<ShoppingListDto>, Pagination>> getShoppingList() {
		return ResponseEntity.ok(shoppingListService.getShoppingList());
	}

	@GetMapping("/shopping-list/{id}")
	@JsonView({ ResponseView.ShoppingListDetailed.class })
	public ResponseEntity<Response<ShoppingListDto, Pagination>> getShoppingListById(@PathVariable String id) {
		return ResponseEntity.ok(shoppingListService.getShoppingListById(id));
	}

	@PutMapping("/shopping-list/{id}")
	public ResponseEntity<Response<Void, Void>> updateShoppingListById(@PathVariable String id,
			@Validated(ShoppingListUpdate.class) @JsonView({
					ShoppingListUpdate.class }) @RequestBody ShoppingListDto shoppingListDto) {
		return ResponseEntity.ok(shoppingListService.updateShoppingListById(id, shoppingListDto));
	}

	@DeleteMapping("/shopping-list/{id}")
	public ResponseEntity<Response<Void, Void>> deleteShoppingListById(@PathVariable String id) {
		return ResponseEntity.ok(shoppingListService.deleteShoppingListById(id));
	}

	@PutMapping("/shopping-list/{id}/item")
	public ResponseEntity<Response<Void, Void>> additemInShoppingList(@PathVariable String id,
			@Validated(ShoppingListAddItem.class) @JsonView({
					ShoppingListAddItem.class }) @RequestBody Collection<ItemDto> shoppingListItemDtos) {
		return ResponseEntity.ok(shoppingListService.additemInShoppingList(id, shoppingListItemDtos));
	}

	@GetMapping("/shopping-list/{id}/item")
	@JsonView({ ResponseView.ShoppingListItem.class })
	public ResponseEntity<Response<Collection<ItemDto>, Pagination>> getShoppingListItems(
			@PathVariable String id) {
		return ResponseEntity.ok(shoppingListService.getShoppingListItems(id));
	}

	@GetMapping("/shopping-list/{id}/item/{productId}")
	@JsonView({ ResponseView.ShoppingListItem.class })
	public ResponseEntity<Response<ItemDto, Void>> getShoppingListItemByProductId(@PathVariable String id,
			@PathVariable String productId) {
		return ResponseEntity.ok(shoppingListService.getShoppingListItemByProductId(id, productId));
	}

	@PutMapping("/shopping-list/{id}/item/{productId}")
	public ResponseEntity<Response<Void, Void>> updateShoppingListItemsByProductId(@PathVariable String id,
			@PathVariable String productId, @Validated(ShoppingListUpdateItem.class) @JsonView({
					ShoppingListUpdateItem.class }) @RequestBody ItemDto shoppingListItemDto) {
		return ResponseEntity
				.ok(shoppingListService.updateShoppingListItemsByProductId(productId, shoppingListItemDto));
	}
}
