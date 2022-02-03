package com.example.shoppinglist.resource.persistance.entity;

import com.example.shoppinglist.resource.persistance.listener.LifecycleListener;
import com.example.shoppinglist.util.AppConstant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RevisionEntity
@Table(name = "revinfo")
@EntityListeners(value = {AuditingEntityListener.class})
public class Revision {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_sequence")
    @SequenceGenerator(
            name = "revision_sequence",
            sequenceName = "revision_sequence",
            allocationSize = AppConstant.SEQUENCE_BATCH_SIZE
    )
    @Column(nullable = false, name = "REV")
    @RevisionNumber
    private long rev;

    @Column(name = "revtstmp")
    @RevisionTimestamp
    private long timestamp;

    @Column(name = "auditor", nullable = false, length = 36)
    @LastModifiedBy
    private String auditor;


}
