package com.example.shoppinglist.resource.persistance.entity;

import com.example.shoppinglist.resource.persistance.audit.Auditable;
import com.example.shoppinglist.util.AppConstant;
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
@Table(name = "item", indexes = {
        @Index(name = "fk_shopping_list_id_index", columnList = "shoppingListId"),
        @Index(name = "uk_shoppingListId_N_productId_index", columnList = "shoppingListId,productId")
})
@DynamicUpdate
@DynamicInsert
public class ItemEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = AppConstant.SEQUENCE_BATCH_SIZE
    )
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "productId", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoppingListId")
    private ShoppingListEntity shoppingList;

    @Column(name = "shoppingListId", insertable = false, updatable = false)
    private Long shoppingListId;
}
