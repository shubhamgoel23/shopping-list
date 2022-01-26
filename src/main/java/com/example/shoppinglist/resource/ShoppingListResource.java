package com.example.shoppinglist.resource;

import com.example.shoppinglist.resource.RequestView.ShoppingListAddItem;
import com.example.shoppinglist.resource.RequestView.ShoppingListCreate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdate;
import com.example.shoppinglist.resource.RequestView.ShoppingListUpdateItem;
import com.example.shoppinglist.util.CustomPage;
import com.example.shoppinglist.util.Response;
import com.example.shoppinglist.util.ResponseBuilder;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@OpenAPIDefinition(info = @Info(title = "Shopping List Service",version = "v1"))
@RestController
@RequestMapping(value = "/api/v1")
@Tag(name = "Shopping List APIs")
public record ShoppingListResource(ShoppingListService shoppingListService) {


    @Operation(summary = "Create shopping list", description = "Name search by %name% format", tags = {"shopping list"})
    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @PostMapping("/shopping-list")
    public ResponseEntity<Response<Void>> createShoppingList(@Validated(ShoppingListCreate.class) @JsonView({
            ShoppingListCreate.class}) @RequestBody ShoppingListDto shoppingListDTO) {
        shoppingListService.createShoppingList(shoppingListDTO);
        return ResponseBuilder.build(HttpStatus.CREATED, "Shopping List Created");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @GetMapping("/shopping-list")
    @JsonView({ResponseView.ShoppingListBasic.class})
    public ResponseEntity<Response<CustomPage<ShoppingListDto>>> getShoppingList(@RequestParam int page,
                                                                                 @RequestParam int size) {
        return ResponseBuilder.build(shoppingListService.getShoppingList(page, size), HttpStatus.OK,
                "shopping list received");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @GetMapping("/shopping-list/{id}")
    @JsonView({ResponseView.ShoppingListDetailed.class})
    public ResponseEntity<Response<ShoppingListDto>> getShoppingListById(@PathVariable String id) {
        return ResponseBuilder.build(shoppingListService.getShoppingListById(id), HttpStatus.OK,
                "shopping list received");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @PutMapping("/shopping-list/{id}")
    public ResponseEntity<Response<Void>> updateShoppingListById(@PathVariable String id,
                                                                 @Validated(ShoppingListUpdate.class) @JsonView({
                                                                         ShoppingListUpdate.class}) @RequestBody ShoppingListDto shoppingListDto) {
        shoppingListService.updateShoppingListById(id, shoppingListDto);
        return ResponseBuilder.build(HttpStatus.OK, "Shopping List updated");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @DeleteMapping("/shopping-list/{id}")
    public ResponseEntity<Response<Void>> deleteShoppingListById(@PathVariable String id) {
        shoppingListService.deleteShoppingListById(id);
        return ResponseBuilder.build(HttpStatus.OK, "Shopping List deleted");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @PutMapping("/shopping-list/{id}/item")
    public ResponseEntity<Response<Void>> addItemInShoppingList(@PathVariable String id,
                                                                @Validated(ShoppingListAddItem.class) @JsonView({
                                                                        ShoppingListAddItem.class}) @RequestBody Map<String, ItemDto> shoppingListItemMap) {
        shoppingListService.addItemInShoppingList(id, shoppingListItemMap);
        return ResponseBuilder.build(HttpStatus.OK, "items added in shopping list");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @GetMapping("/shopping-list/{id}/item")
    @JsonView({ResponseView.ShoppingListItem.class})
    public ResponseEntity<Response<CustomPage<ItemDto>>> getShoppingListItems(@PathVariable String id,
                                                                              @RequestParam int page, @RequestParam int size) {
        return ResponseBuilder.build(shoppingListService.getShoppingListItems(id, page, size), HttpStatus.OK,
                "shopping list items received");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @GetMapping("/shopping-list/{id}/item/{productId}")
    @JsonView({ResponseView.ShoppingListItem.class})
    public ResponseEntity<Response<ItemDto>> getShoppingListItemByProductId(@PathVariable String id,
                                                                            @PathVariable String productId) {
        return ResponseBuilder.build(shoppingListService.getShoppingListItemByProductId(id, productId), HttpStatus.OK,
                "shopping list items received");
    }

    @Parameter(in = ParameterIn.HEADER,name = "X-Tenant",required = true,schema = @Schema(type = "string",name = "X-Tenant"))
    @Parameter(in = ParameterIn.HEADER,name = "X-Customer",required = true,schema = @Schema(type = "string",name = "X-Customer"))
    @PutMapping("/shopping-list/{id}/item/{productId}")
    public ResponseEntity<Response<Void>> updateShoppingListItemsByProductId(@PathVariable String id,
                                                                             @PathVariable String productId, @Validated(ShoppingListUpdateItem.class) @JsonView({
            ShoppingListUpdateItem.class}) @RequestBody ItemDto shoppingListItemDto) {
        shoppingListService.updateShoppingListItemsByProductId(id, productId, shoppingListItemDto);
        return ResponseBuilder.build(HttpStatus.OK, "item updated");
    }
}
