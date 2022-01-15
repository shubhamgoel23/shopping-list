package com.example.shoppinglist.resource;

import com.example.shoppinglist.exception.BusinessException;
import com.example.shoppinglist.exception.BusinessExceptionReason;
import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import com.example.shoppinglist.resource.persistance.repository.ItemEntityRepository;
import com.example.shoppinglist.resource.persistance.repository.ShoppingListRepository;
import com.example.shoppinglist.util.CustomPage;
import com.example.shoppinglist.util.CustomPagebale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ItemEntityRepository itemEntityRepository;
    private final ShoppingListMapper shoppingListMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createShoppingList(ShoppingListDto shoppingListDTO) {
        shoppingListRepository
                .save(ShoppingListEntity.builder()
                        .listId(UUID.randomUUID().toString())
                        .name(shoppingListDTO.name())
                        .type(shoppingListDTO.type())
                        .build());
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public CustomPage<ShoppingListDto> getShoppingList(int page, int size) {
        var shoppingListPage = shoppingListRepository.findAll(PageRequest.of(page, size));

        return CustomPage.<ShoppingListDto>builder()
                .content(shoppingListMapper.fromShoppingListEntity(shoppingListPage.getContent()))
                .pageable(CustomPagebale.builder().pageNumber(shoppingListPage.getPageable().getPageNumber())
                        .pageSize(shoppingListPage.getPageable().getPageSize())
                        .totalElements(shoppingListPage.getTotalElements()).build())
                .build();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public ShoppingListDto getShoppingListById(Long id) {
        var shoppingListEntity = shoppingListRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));
        return shoppingListMapper.fromShoppingListEntity(shoppingListEntity);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void updateShoppingListById(Long id, ShoppingListDto shoppingListDto) {
        var shoppingListEntity = shoppingListRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));
        shoppingListEntity.setName(shoppingListDto.name());
        shoppingListRepository.save(shoppingListEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void deleteShoppingListById(Long id) {
        if (!shoppingListRepository.existsById(id))
            throw new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID);
        shoppingListRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void additemInShoppingList(Long id, Map<String, ItemDto> shoppingListItemMap) {
        var shoppingListEntity = shoppingListRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID));

        var items = itemEntityRepository.findAllByProductIdInAndShoppingListId(shoppingListItemMap.keySet(), shoppingListEntity.getId());

        var entityMap = items.stream().collect(Collectors.toMap(ItemEntity::getProductId, Function.identity()));

        var entitiesToBeDeleted = new ArrayList<ItemEntity>();
        var entitiesToBeAdded = new ArrayList<ItemEntity>();
        shoppingListItemMap.forEach((k, v) -> {
            if (v.quantity() <= 0 && entityMap.containsKey(k)) {
                entitiesToBeDeleted.add(entityMap.get(k));
                entityMap.remove(k);
            } else if (v.quantity() > 0 && entityMap.containsKey(k)) {
                var itemEntity = entityMap.get(k);
                itemEntity.setQuantity(v.quantity());
            } else if (v.quantity() > 0 && !entityMap.containsKey(k)) {
                var itemEntity = ItemEntity.builder().productId(k).quantity(v.quantity())
                        .shoppingList(shoppingListEntity).build();
                entitiesToBeAdded.add(itemEntity);
            }
        });

        itemEntityRepository.saveAll(entitiesToBeAdded);
        itemEntityRepository.deleteAll(entitiesToBeDeleted);

    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public CustomPage<ItemDto> getShoppingListItems(Long id, int page, int size) {
        if (!shoppingListRepository.existsById(id))
            throw new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID);

        var items = itemEntityRepository.findAllByShoppingListId(id, PageRequest.of(page, size));
        return CustomPage.<ItemDto>builder().content(shoppingListMapper.fromItemEntity(items.getContent()))
                .pageable(CustomPagebale.builder().pageNumber(items.getPageable().getPageNumber())
                        .pageSize(items.getPageable().getPageSize()).totalElements(items.getTotalElements()).build())
                .build();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public ItemDto getShoppingListItemByProductId(Long id, String productId) {
        if (!shoppingListRepository.existsById(id))
            throw new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID);

        var entity = itemEntityRepository.findByProductIdAndShoppingListId(productId, id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_PRODUCT_ID));
        return shoppingListMapper.fromItemEntity(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void updateShoppingListItemsByProductId(Long id, String productId, ItemDto itemDto) {
        if (!shoppingListRepository.existsById(id))
            throw new BusinessException(BusinessExceptionReason.INVALID_SHOPPING_LIST_ID);

        var entity = itemEntityRepository.findByProductIdAndShoppingListId(productId, id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_PRODUCT_ID));
        entity.setQuantity(itemDto.quantity());
    }
}
