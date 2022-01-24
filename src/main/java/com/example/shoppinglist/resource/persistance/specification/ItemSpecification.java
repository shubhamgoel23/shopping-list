package com.example.shoppinglist.resource.persistance.specification;

import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ItemEntity_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class ItemSpecification {

    public static Specification<ItemEntity> belongsToShoppingListId(Long id) {
        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get(ItemEntity_.SHOPPING_LIST).get(ShoppingListEntity_.LIST_ID), id);
                criteriaBuilder.equal(root.get(ItemEntity_.SHOPPING_LIST_ID), id);
    }

    public static Specification<ItemEntity> belongsToProductId(String productId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ItemEntity_.PRODUCT_ID), productId);
    }

    public static Specification<ItemEntity> belongsToProductIds(Iterable<String> productIds) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get(ItemEntity_.PRODUCT_ID)).value(productIds);
    }
}
