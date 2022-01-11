package com.example.shoppinglist.resource.persistance.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
@Table(name = "shopping_list")
@DynamicUpdate
@DynamicInsert
public class ShoppingListEntity extends Auditable {

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "type", nullable = false, updatable = false)
	private String type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingList")
	@Builder.Default
	@ToString.Exclude
	private List<ItemEntity> items = new ArrayList<>();

}
