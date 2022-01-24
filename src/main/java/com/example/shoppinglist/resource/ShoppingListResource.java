package com.example.shoppinglist.resource;

import com.example.shoppinglist.resource.RequestView.ShoppingListAddItem;
import com.example.shoppinglist.resource.RequestView.ShoppingListCreate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdateItem;
import com.example.shoppinglist.util.CustomPage;
import com.example.shoppinglist.util.Response;
import com.example.shoppinglist.util.ResponseBuilder;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
public record ShoppingListResource(ShoppingListService shoppingListService) {

    @PostMapping("/shopping-list")
    public ResponseEntity<Response<Void>> createShoppingList(@Validated(ShoppingListCreate.class) @JsonView({
            ShoppingListCreate.class}) @RequestBody ShoppingListDto shoppingListDTO) {
        shoppingListService.createShoppingList(shoppingListDTO);
        return ResponseBuilder.build(HttpStatus.CREATED, "Shopping List Created");
    }

    @GetMapping("/shopping-list")
    @JsonView({ResponseView.ShoppingListBasic.class})
    public ResponseEntity<Response<CustomPage<ShoppingListDto>>> getShoppingList(@RequestParam int page,
                                                                                 @RequestParam int size) {
        return ResponseBuilder.build(shoppingListService.getShoppingList(page, size), HttpStatus.OK,
                "shopping list received");
    }

    @GetMapping("/shopping-list/{id}")
    @JsonView({ResponseView.ShoppingListDetailed.class})
    public ResponseEntity<Response<ShoppingListDto>> getShoppingListById(@PathVariable Long id) {
        return ResponseBuilder.build(shoppingListService.getShoppingListById(id), HttpStatus.OK,
                "shopping list received");
    }

    @PutMapping("/shopping-list/{id}")
    public ResponseEntity<Response<Void>> updateShoppingListById(@PathVariable Long id,
                                                                 @Validated(ShoppingListUpdate.class) @JsonView({
                                                                         ShoppingListUpdate.class}) @RequestBody ShoppingListDto shoppingListDto) {
        shoppingListService.updateShoppingListById(id, shoppingListDto);
        return ResponseBuilder.build(HttpStatus.OK, "Shopping List updated");
    }

    @DeleteMapping("/shopping-list/{id}")
    public ResponseEntity<Response<Void>> deleteShoppingListById(@PathVariable Long id) {
        shoppingListService.deleteShoppingListById(id);
        return ResponseBuilder.build(HttpStatus.OK, "Shopping List deleted");
    }

    @PutMapping("/shopping-list/{id}/item")
    public ResponseEntity<Response<Void>> additemInShoppingList(@PathVariable Long id,
                                                                @Validated(ShoppingListAddItem.class) @JsonView({
                                                                        ShoppingListAddItem.class}) @RequestBody Map<String, ItemDto> shoppingListItemMap) {
        shoppingListService.additemInShoppingList(id, shoppingListItemMap);
        return ResponseBuilder.build(HttpStatus.OK, "items added in shopping list");
    }

    @GetMapping("/shopping-list/{id}/item")
    @JsonView({ResponseView.ShoppingListItem.class})
    public ResponseEntity<Response<CustomPage<ItemDto>>> getShoppingListItems(@PathVariable Long id,
                                                                              @RequestParam int page, @RequestParam int size) {
        return ResponseBuilder.build(shoppingListService.getShoppingListItems(id, page, size), HttpStatus.OK,
                "shopping list items received");
    }

    @GetMapping("/shopping-list/{id}/item/{productId}")
    @JsonView({ResponseView.ShoppingListItem.class})
    public ResponseEntity<Response<ItemDto>> getShoppingListItemByProductId(@PathVariable Long id,
                                                                            @PathVariable String productId) {
        return ResponseBuilder.build(shoppingListService.getShoppingListItemByProductId(id, productId), HttpStatus.OK,
                "shopping list items received");
    }

    @PutMapping("/shopping-list/{id}/item/{productId}")
    public ResponseEntity<Response<Void>> updateShoppingListItemsByProductId(@PathVariable Long id,
                                                                             @PathVariable String productId, @Validated(ShoppingListUpdateItem.class) @JsonView({
            ShoppingListUpdateItem.class}) @RequestBody ItemDto shoppingListItemDto) {
        shoppingListService.updateShoppingListItemsByProductId(id, productId, shoppingListItemDto);
        return ResponseBuilder.build(HttpStatus.OK, "item updated");
    }
}
