package com.example.shoppinglist.resource.persistance.specification;

import com.example.shoppinglist.resource.ShoppingListType;
import com.example.shoppinglist.resource.context.CustomerContext;
import com.example.shoppinglist.resource.context.TenantContext;
import com.example.shoppinglist.resource.persistance.audit.Auditable_;
import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ItemEntity_;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

@UtilityClass
public class ShoppingListSpecification {

    public static Specification<ShoppingListEntity> equalsToName(@NonNull String name) {
        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.like(root.get(ShoppingListEntity_.NAME), "%" + name + "%");
                criteriaBuilder.equal(criteriaBuilder.upper(root.get(ShoppingListEntity_.NAME)), name.toUpperCase());
    }

    public static Specification<ShoppingListEntity> equalsToType(@NonNull ShoppingListType type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.get(ShoppingListEntity_.TYPE)), type);
    }

    public static Specification<ShoppingListEntity> belongsToTenantId() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.get(Auditable_.TENANT_ID)), TenantContext.getTenantId().toUpperCase());
    }

    public static Specification<ShoppingListEntity> belongsToCustomerId() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.get(ShoppingListEntity_.CUSTOMER_ID)), CustomerContext.getCustomerId().toUpperCase());
    }

    public static Specification<ShoppingListEntity> belongsToListId(@NonNull String listId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.get(ShoppingListEntity_.LIST_ID)), listId.toUpperCase());
    }

    public static Specification<ShoppingListEntity> belongsToItemProductId(@Nullable String productId) {
        return (root, query, criteriaBuilder) ->{
            if(ObjectUtils.isEmpty(productId))
                return criteriaBuilder.conjunction();
            final Join<ShoppingListEntity, ItemEntity> items = root.join(ShoppingListEntity_.ITEMS, JoinType.LEFT);
            return criteriaBuilder.equal(criteriaBuilder.upper(items.get(ItemEntity_.PRODUCT_ID)), productId.toUpperCase());
        };
    }
}
