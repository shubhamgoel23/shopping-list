package com.example.shoppinglist.resource;

import com.example.shoppinglist.util.CustomPage;
import com.example.shoppinglist.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v2")
public class TestController {

    @GetMapping("/shopping-list")
    public ResponseEntity<Response<CustomPage<ShoppingListDto>>> getShoppingList() {
        return null;
    }
}
