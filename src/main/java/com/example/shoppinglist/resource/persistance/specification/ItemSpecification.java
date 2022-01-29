package com.example.shoppinglist.resource.persistance.specification;

import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ItemEntity_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@UtilityClass
public class ItemSpecification {

    public static Specification<ItemEntity> belongsToShoppingListId(Long id) {
        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get(ItemEntity_.SHOPPING_LIST).get(ShoppingListEntity_.LIST_ID), id);
                criteriaBuilder.equal(root.get(ItemEntity_.SHOPPING_LIST_ID), id);
    }

    public static Specification<ItemEntity> belongsToProductId(String productId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.get(ItemEntity_.PRODUCT_ID)), productId.toUpperCase());
    }

    public static Specification<ItemEntity> belongsToProductIds(@NonNull Iterable<String> productIds) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.upper(root.get(ItemEntity_.PRODUCT_ID)).in(StreamSupport
                        .stream(productIds.spliterator(), false)
                        .map(String::toUpperCase)
                        .collect(Collectors.toSet()));
    }
}
