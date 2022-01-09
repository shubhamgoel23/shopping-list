package com.example.shoppinglist.resource.persistance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppinglist.resource.persistance.entity.ItemEntity;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long> {

	Page<ItemEntity> findAllByShoppingListId(Long id, Pageable pageable);

	Optional<ItemEntity> findByProductIdAndShoppingListId(String productId, Long id);

}
