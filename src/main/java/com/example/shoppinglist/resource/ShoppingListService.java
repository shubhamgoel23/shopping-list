package com.example.shoppinglist.resource;

import com.example.shoppinglist.exception.BusinessException;
import com.example.shoppinglist.exception.BusinessExceptionReason;
import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import com.example.shoppinglist.resource.persistance.repository.ItemEntityRepository;
import com.example.shoppinglist.resource.persistance.repository.ShoppingListRepository;
import com.example.shoppinglist.util.CustomPage;
import com.example.shoppinglist.util.CustomPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.shoppinglist.resource.persistance.specification.ItemSpecification.*;
import static com.example.shoppinglist.resource.persistance.specification.ShoppingListSpecification.*;
import static com.example.shoppinglist.util.HelperClass.uuid;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ItemEntityRepository itemEntityRepository;
    private final ShoppingListMapper shoppingListMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createShoppingList(ShoppingListDto shoppingListDTO) {
        Optional<ShoppingListEntity> shoppingListEntity = shoppingListRepository.findOne(where(belongsToTenantId())
                .and(belongsToCustomerId())
                .and(equalsToName(shoppingListDTO.name()))
                .and(equalsToType(shoppingListDTO.type()))
        );
        if (shoppingListEntity.isPresent())
            throw new BusinessException(BusinessExceptionReason.SHOPPING_LIST_NAME_ALREADY_PRESENT);

        shoppingListRepository
                .save(ShoppingListEntity.builder()
                        .listId(uuid.get())
                        .name(shoppingListDTO.name())
                        .type(shoppingListDTO.type())
                        .build());
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public CustomPage<ShoppingListDto> getShoppingList(int page, int size) {

        var shoppingListPage = shoppingListRepository.findAll(where(belongsToTenantId()
                        .and(belongsToCustomerId()))
                , PageRequest.of(page, size));

        return CustomPage.<ShoppingListDto>builder()
                .content(shoppingListMapper
                        .fromShoppingListEntity(shoppingListPage.getContent()))
                .pageable(CustomPageable.builder()
                        .pageNumber(shoppingListPage.getPageable().getPageNumber())
                        .pageSize(shoppingListPage.getPageable().getPageSize())
                        .totalElements(shoppingListPage.getTotalElements())
                        .build())
                .build();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public ShoppingListDto getShoppingListById(String listId) {

        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId))))
        ).orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        return shoppingListMapper.fromShoppingListEntity(shoppingListEntity);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void updateShoppingListById(String listId, ShoppingListDto shoppingListDto) {
        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId))))
        ).orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        shoppingListEntity.setName(shoppingListDto.name());
        shoppingListRepository.save(shoppingListEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void deleteShoppingListById(String listId) {
        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId))))
        ).orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        shoppingListRepository.delete(shoppingListEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void addItemInShoppingList(String listId, Map<String, ItemDto> shoppingListItemMap) {
        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId))))
        ).orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        var items = itemEntityRepository.findAll(
                where(belongsToProductIds(shoppingListItemMap.keySet())
                        .and(belongsToShoppingListId(shoppingListEntity.getId()))));

        var entityMap = items.stream().collect(Collectors.toMap(item -> item.getProductId().toUpperCase(), Function.identity()));

        var entitiesToBeDeleted = new ArrayList<ItemEntity>();
        var entitiesToBeAdded = new ArrayList<ItemEntity>();
        shoppingListItemMap.forEach((k, v) -> {
            if (v.quantity() <= 0 && entityMap.containsKey(k.toUpperCase())) {
                entitiesToBeDeleted.add(entityMap.get(k.toUpperCase()));
                entityMap.remove(k.toUpperCase());
            } else if (v.quantity() > 0 && entityMap.containsKey(k.toUpperCase())) {
                var itemEntity = entityMap.get(k.toUpperCase());
                itemEntity.setQuantity(v.quantity());
            } else if (v.quantity() > 0 && !entityMap.containsKey(k.toUpperCase())) {
                var itemEntity = ItemEntity.builder()
                        .productId(k)
                        .quantity(v.quantity())
                        .shoppingList(shoppingListEntity)
                        .build();
                entitiesToBeAdded.add(itemEntity);
            }
        });

        itemEntityRepository.saveAll(entitiesToBeAdded);
        itemEntityRepository.deleteAll(entitiesToBeDeleted);

    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public CustomPage<ItemDto> getShoppingListItems(String listId, int page, int size) {

        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId))))
        ).orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        var items = itemEntityRepository.findAll(
                where(belongsToShoppingListId(shoppingListEntity.getId())), PageRequest.of(page, size));

        return CustomPage.<ItemDto>builder().content(shoppingListMapper.fromItemEntity(items.getContent()))
                .pageable(CustomPageable.builder().pageNumber(items.getPageable().getPageNumber())
                        .pageSize(items.getPageable().getPageSize()).totalElements(items.getTotalElements()).build())
                .build();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public ItemDto getShoppingListItemByProductId(String listId, String productId) {
        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId))))
        ).orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        var entity = itemEntityRepository.findOne(
                        where(belongsToShoppingListId(shoppingListEntity.getId()).and(belongsToProductId(productId))))
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_PRODUCT_ID));
        return shoppingListMapper.fromItemEntity(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void updateShoppingListItemsByProductId(String listId, String productId, ItemDto itemDto) {
        var shoppingListEntity = shoppingListRepository.findOne(
                where(belongsToTenantId()
                        .and(belongsToCustomerId().and(belongsToListId(listId))))
        ).orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        var entity = itemEntityRepository.findOne(
                        where(belongsToShoppingListId(shoppingListEntity.getId()).and(belongsToProductId(productId))))
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_PRODUCT_ID));
        entity.setQuantity(itemDto.quantity());
    }
}
