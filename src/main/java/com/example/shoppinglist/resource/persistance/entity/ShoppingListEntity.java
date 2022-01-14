package com.example.shoppinglist.resource.persistance.entity;

import com.example.shoppinglist.resource.persistance.audit.Auditable;
import com.example.shoppinglist.util.AppConstant;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

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
@Table(name = "shopping_list")
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

    @NaturalId
    @Column(name = "listId", nullable = false, unique = true, updatable = false)
    private String listId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false, updatable = false)
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingList")
    @Builder.Default
    @ToString.Exclude
    private List<ItemEntity> items = new ArrayList<>();

}
