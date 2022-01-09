package com.example.shoppinglist.resource.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.shoppinglist.resource.persistance.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "item")
public class ItemEntity extends Auditable {

	@Column(name = "productId", nullable = false)
	private String productId;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;
}
