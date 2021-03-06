package com.example.shoppinglist.resource;

import com.example.shoppinglist.resource.RequestView.ShoppingListAddItem;
import com.example.shoppinglist.resource.RequestView.ShoppingListCreate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdateItem;
import com.example.shoppinglist.util.CustomPage;
import com.example.shoppinglist.util.Response;
import com.example.shoppinglist.util.ResponseBuilder;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1")
@Tag(name = "Shopping List APIs")
@RequiredArgsConstructor
public class ShoppingListResource {

    private final ShoppingListService shoppingListService;
    private final ShoppingListVersionService shoppingListVersionService;

    @Operation(summary = "Create shopping list", description = "Create shopping list")
    @PostMapping("/shopping-list")
    public ResponseEntity<Response<Void>> createShoppingList(
            @Validated(ShoppingListCreate.class) @JsonView({ShoppingListCreate.class})
            @RequestBody ShoppingListDto shoppingListDTO) {
        shoppingListService.createShoppingList(shoppingListDTO);
        return ResponseBuilder.build(HttpStatus.CREATED, "Shopping List Created");
    }

    @Operation(summary = "Get all shopping list", description = "Get all shopping list")
    @GetMapping("/shopping-list")
    @JsonView({ResponseView.ShoppingListBasic.class})
    public ResponseEntity<Response<CustomPage<ShoppingListDto>>> getShoppingList(@RequestParam(defaultValue = "0", required = false) int page,
                                                                                 @RequestParam(defaultValue = "0", required = false) int size,
                                                                                 @RequestParam(required = false) String productId) {
        return ResponseBuilder.build(shoppingListService.getShoppingList(page, size, productId), HttpStatus.OK,
                "shopping list received");
    }

    @Operation(summary = "Get shopping list by id", description = "Get shopping list by id")
    @GetMapping("/shopping-list/{id}")
    @JsonView({ResponseView.ShoppingListDetailed.class})
    public ResponseEntity<Response<ShoppingListDto>> getShoppingListById(@PathVariable String id, WebRequest webRequest) {

        String version = shoppingListVersionService.shoppingListVerion(id).orElse(null);

        if (webRequest.checkNotModified(version))
            return null;

        var res = shoppingListService.getShoppingListById(id);
        return ResponseBuilder.build(res, HttpStatus.OK,
                "shopping list received", version);
    }

    @Operation(summary = "update shopping list", description = "update shopping list")
    @PutMapping("/shopping-list/{id}")
    public ResponseEntity<Response<Void>> updateShoppingListById(@PathVariable String id,
                                                                 @Validated(ShoppingListUpdate.class) @JsonView({
                                                                         ShoppingListUpdate.class}) @RequestBody ShoppingListDto shoppingListDto) {
        shoppingListService.updateShoppingListById(id, shoppingListDto);
        return ResponseBuilder.build(HttpStatus.OK, "Shopping List updated");
    }

    @Operation(summary = "delete shopping list", description = "delete shopping list")
    @DeleteMapping("/shopping-list/{id}")
    public ResponseEntity<Response<Void>> deleteShoppingListById(@PathVariable String id) {
        shoppingListService.deleteShoppingListById(id);
        return ResponseBuilder.build(HttpStatus.OK, "Shopping List deleted");
    }

    @Operation(summary = "add/update shopping list products", description = "add/update shopping list products")
    @PutMapping("/shopping-list/{id}/item")
    public ResponseEntity<Response<Void>> addItemInShoppingList(@PathVariable String id,
                                                                @Validated(ShoppingListAddItem.class) @JsonView({
                                                                        ShoppingListAddItem.class}) @RequestBody Map<String, ItemDto> shoppingListItemMap) {
        shoppingListService.addItemInShoppingList(id, shoppingListItemMap);
        return ResponseBuilder.build(HttpStatus.OK, "items added in shopping list");
    }

    @Operation(summary = "Get all shopping list products", description = "Get all shopping list products")
    @GetMapping("/shopping-list/{id}/item")
    @JsonView({ResponseView.ShoppingListItem.class})
    public ResponseEntity<Response<CustomPage<ItemDto>>> getShoppingListItems(@PathVariable String id,
                                                                              @RequestParam(defaultValue = "0", required = false) int page,
                                                                              @RequestParam(defaultValue = "10", required = false) int size,
                                                                              WebRequest webRequest) {

        String version = shoppingListVersionService.shoppingListVerion(id).orElse(null);

        if (webRequest.checkNotModified(version))
            return null;

        return ResponseBuilder.build(shoppingListService.getShoppingListItems(id, page, size), HttpStatus.OK,
                "shopping list items received", version);
    }

    @Operation(summary = "Get shopping list product by id", description = "Get shopping list product by id")
    @GetMapping("/shopping-list/{id}/item/{productId}")
    @JsonView({ResponseView.ShoppingListItem.class})
    public ResponseEntity<Response<ItemDto>> getShoppingListItemByProductId(@PathVariable String id,
                                                                            @PathVariable String productId) {
        return ResponseBuilder.build(shoppingListService.getShoppingListItemByProductId(id, productId), HttpStatus.OK,
                "shopping list items received");
    }

    @Operation(summary = "update shopping list", description = "update shopping list")
    @PutMapping("/shopping-list/{id}/item/{productId}")
    public ResponseEntity<Response<Void>> updateShoppingListItemsByProductId(@PathVariable String id,
                                                                             @PathVariable String productId,
                                                                             @Validated(ShoppingListUpdateItem.class) @JsonView({
                                                                                     ShoppingListUpdateItem.class}) @RequestBody ItemDto shoppingListItemDto) {
        shoppingListService.updateShoppingListItemsByProductId(id, productId, shoppingListItemDto);
        return ResponseBuilder.build(HttpStatus.OK, "item updated");
    }
}
