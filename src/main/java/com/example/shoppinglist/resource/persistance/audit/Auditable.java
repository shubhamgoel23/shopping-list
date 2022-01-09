package com.example.shoppinglist.resource.persistance.audit;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.shoppinglist.resource.persistance.listener.LifecycleListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class, LifecycleListener.class })
public abstract class Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Version
	private Long version;

	@Column(name = "createdOn", nullable = false, updatable = false)
	@CreatedDate
	private Long createdOn;

	@Column(name = "updatedOn")
	@LastModifiedDate
	private Long updatedOn;

}