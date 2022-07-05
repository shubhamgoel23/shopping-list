package com.example.shoppinglist.resource.persistance.entity;

import com.example.shoppinglist.resource.persistance.audit.Auditable;
import com.example.shoppinglist.util.AppConstant;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import javax.persistence.*;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item", indexes = {
        @Index(name = "in_shoppingListId", columnList = "shoppingListId"),
        @Index(name = "in_shoppingListId_n_productId", columnList = "shoppingListId,productId")
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

    @Setter(AccessLevel.NONE)
    @Column(name = "productId", nullable = false, updatable = false, length = 36)
    private String productId;

    @Audited
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoppingListId")
    private ShoppingListEntity shoppingList;

    @Column(name = "shoppingListId", insertable = false, updatable = false)
    private Long shoppingListId;

    @PrePersist
    @PreRemove
    @PreUpdate
    private void isModified() {
        this.shoppingList.setUpdatedOn(this.getUpdatedOn());
    }
}
