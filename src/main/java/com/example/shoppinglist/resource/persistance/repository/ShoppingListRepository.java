package com.example.shoppinglist.resource.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppinglist.resource.persistance.entity.ShoppingListEntity;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Long> {

}
