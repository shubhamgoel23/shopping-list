package com.example.shoppinglist.resource;

import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import com.example.shoppinglist.resource.persistance.repository.ItemEntityRepository;
import com.example.shoppinglist.resource.persistance.repository.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.shoppinglist.resource.persistance.specification.ItemSpecification.belongsToProductId;
import static com.example.shoppinglist.resource.persistance.specification.ItemSpecification.belongsToShoppingListId;
import static com.example.shoppinglist.resource.persistance.specification.ShoppingListSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingListVersionService {

    private final ShoppingListRepository shoppingListRepository;
    private final ItemEntityRepository itemEntityRepository;

    public Optional<String> shoppingListVerion(String id) {
        return shoppingListRepository.findOne(
                        where(belongsToTenantId()
                                .and(belongsToCustomerId().and(belongsToListId(id)))))
                .map(ShoppingListEntity::getVersion)
                .map(String::valueOf);
    }

    public Optional<String> productVerion(String listId, String productId) {
        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId)))));

        if (shoppingListEntity.isEmpty())
            return Optional.empty();

        return itemEntityRepository.findOne(
                        where(belongsToShoppingListId(shoppingListEntity.get().getId()).and(belongsToProductId(productId))))
                .map(ItemEntity::getVersion)
                .map(String::valueOf);
    }
}
