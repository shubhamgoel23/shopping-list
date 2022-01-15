package com.example.shoppinglist.resource.persistance.repository;

import com.example.shoppinglist.resource.persistance.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long> {

    Page<ItemEntity> findAllByShoppingListId(Long id, Pageable pageable);

    Optional<ItemEntity> findByProductIdAndShoppingListId(String productId, Long id);

    List<ItemEntity> findAllByProductIdInAndShoppingListId(Iterable<String> productIds, Long id);

}
