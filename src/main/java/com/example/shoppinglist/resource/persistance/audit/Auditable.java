package com.example.shoppinglist.resource.persistance.audit;

import com.example.shoppinglist.resource.persistance.listener.LifecycleListener;
import com.example.shoppinglist.resource.persistance.listener.TenantGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class, LifecycleListener.class})
public abstract class Auditable {

    @Column(name = "version")
    @Version
    private Long version;

    @Column(name = "createdOn", nullable = false, updatable = false)
    @CreatedDate
    private Long createdOn;

    @Setter
    @Column(name = "updatedOn", nullable = false)
    @LastModifiedDate
    private Long updatedOn;

    @Column(name = "created_by", nullable = false, updatable = false, length = 36)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by", nullable = false, length = 36)
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "tenantId", nullable = false, updatable = false, length = 36)
    @GeneratorType(type = TenantGenerator.class,
    when = GenerationTime.INSERT)
    private String tenantId;

//    @PrePersist
//    private void setTenantId() {
//        this.tenantId = TenantContext.getTenantId();
//    }

}