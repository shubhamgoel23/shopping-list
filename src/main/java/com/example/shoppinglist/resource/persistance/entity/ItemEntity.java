package com.example.shoppinglist.resource.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.shoppinglist.resource.persistance.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
