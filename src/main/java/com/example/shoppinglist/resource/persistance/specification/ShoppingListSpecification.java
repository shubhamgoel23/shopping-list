package com.example.shoppinglist.resource.persistance.specification;

import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class ShoppingListSpecification {

    public static Specification<ShoppingListEntity> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ShoppingListEntity_.NAME), "%" + name + "%");
    }
}
