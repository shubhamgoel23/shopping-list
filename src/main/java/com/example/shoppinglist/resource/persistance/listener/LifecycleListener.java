package com.example.shoppinglist.resource.persistance.listener;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class LifecycleListener {

    @PrePersist
    public void prePersist(Object o) {
        log.debug("prePersist {}", o);
    }

    @PostPersist
    public void postPersist(Object o) {
        log.debug("postPersist {}", o);
    }

    @PreRemove
    public void preRemove(Object o) {
        log.debug("preRemove {}", o);
    }

    @PostRemove
    public void postRemove(Object o) {
        log.debug("postRemove {}", o);
    }

    @PreUpdate
    public void preUpdate(Object o) {
        log.debug("preUpdate {}", o);
    }

    @PostUpdate
    public void postUpdate(Object o) {
        log.debug("postUpdate {}", o);
    }

    @PostLoad
    public void postLoad(Object o) {
        log.debug("postLoad {}", o);
    }
}