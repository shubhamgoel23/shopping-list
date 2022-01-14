package com.example.shoppinglist.resource.persistance.repository;

import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Long> {

}
