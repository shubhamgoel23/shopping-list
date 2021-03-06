package com.example.shoppinglist.resource.persistance.entity;

import com.example.shoppinglist.resource.ShoppingListType;
import com.example.shoppinglist.resource.context.CustomerContext;
import com.example.shoppinglist.resource.persistance.ShoppingListTypeConverter;
import com.example.shoppinglist.resource.persistance.audit.Auditable;
import com.example.shoppinglist.util.AppConstant;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping_list", indexes = {
        @Index(name = "in_customerId_n_tenantId", columnList = "customerId,tenantId"),
        @Index(name = "in_name_n_type_n_customerId_tenantId", columnList = "name,type,customerId,tenantId", unique = true)
})
@DynamicUpdate
@DynamicInsert
public class ShoppingListEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "list_sequence")
    @SequenceGenerator(
            name = "list_sequence",
            sequenceName = "list_sequence",
            allocationSize = AppConstant.SEQUENCE_BATCH_SIZE
    )
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Setter(AccessLevel.NONE)
    @NaturalId
    @Column(name = "listId", nullable = false, unique = true, updatable = false, length = 36)
    private String listId;

    @Audited
    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Setter(AccessLevel.NONE)
    @Convert(converter = ShoppingListTypeConverter.class)
    @Column(name = "type", nullable = false, updatable = false, length = 5)
    private ShoppingListType type;

    @Setter(AccessLevel.NONE)
    @Column(name = "customerId", nullable = false, updatable = false, length = 36)
    private String customerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingList", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<ItemEntity> items = new ArrayList<>();

    @PrePersist
    private void setCustomerId() {
        this.customerId = CustomerContext.getCustomerId();
    }

}
