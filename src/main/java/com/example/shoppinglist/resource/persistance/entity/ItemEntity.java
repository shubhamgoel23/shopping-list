package com.example.shoppinglist.resource.persistance.entity;

import com.example.shoppinglist.resource.persistance.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
@DynamicUpdate
@DynamicInsert
public class ItemEntity extends Auditable {

    @Column(name = "productId", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private ShoppingListEntity shoppingList;
}
